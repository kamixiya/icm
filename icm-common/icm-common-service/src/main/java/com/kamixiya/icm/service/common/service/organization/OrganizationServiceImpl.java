package com.kamixiya.icm.service.common.service.organization;

import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.common.SimpleTreeDataDTO;
import com.kamixiya.icm.model.organization.department.DepartmentBaseDTO;
import com.kamixiya.icm.model.organization.employee.EmployeeDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.model.organization.position.PositionBaseDTO;
import com.kamixiya.icm.model.organization.position.PositionDTO;
import com.kamixiya.icm.model.organization.unit.UnitBaseDTO;
import com.kamixiya.icm.model.security.role.RoleDTO;
import com.kamixiya.icm.service.common.entity.organization.Employee;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.organization.Position;
import com.kamixiya.icm.service.common.entity.security.Role;
import com.kamixiya.icm.service.common.entity.security.User;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.filter.CurrentUserUtil;
import com.kamixiya.icm.service.common.mapper.organization.*;
import com.kamixiya.icm.service.common.mapper.security.RoleMapper;
import com.kamixiya.icm.service.common.mapper.security.UserMapper;
import com.kamixiya.icm.service.common.repository.organization.EmployeeRepository;
import com.kamixiya.icm.service.common.repository.organization.OrganizationRepository;
import com.kamixiya.icm.service.common.repository.organization.PositionRepository;
import com.kamixiya.icm.service.common.repository.security.RoleRepository;
import com.kamixiya.icm.service.common.repository.security.UserRepository;
import com.kamixiya.icm.service.common.service.security.RoleService;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import com.kamixiya.icm.service.common.utils.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

import static java.lang.Long.parseLong;

/**
 * OrganizationServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Slf4j
@Service("organizationService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final PositionMapper positionMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final UnitMapper unitMapper;
    private final DepartmentMapper departmentMapper;
    private final OrganizationTreeMapper organizationTreeMapper;
    private final PositionRepository positionRepository;

    @Autowired
    public OrganizationServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, UserMapper userMapper, OrganizationRepository organizationRepository, OrganizationMapper organizationMapper,
                                   EmployeeMapper employeeMapper, PositionMapper positionMapper, EmployeeRepository employeeRepository, UserRepository userRepository,
                                   PositionRepository positionRepository, RoleService roleService, UnitMapper unitMapper, DepartmentMapper departmentMapper, OrganizationTreeMapper organizationTreeMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
        this.positionMapper = positionMapper;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.unitMapper = unitMapper;
        this.departmentMapper = departmentMapper;
        this.organizationTreeMapper = organizationTreeMapper;

        this.positionRepository = positionRepository;
    }

    @Override
    public Organization findById(Long id) {
        Optional<Organization> org = organizationRepository.findById(id);
        if (!org.isPresent()) {
            throw new NoSuchDataException(id.toString());
        }
        return org.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = {"Positions"}, allEntries = true)
    public List<OrganizationDTO> changeParent(String childId, String parentId, List<String> ids) {
        List<OrganizationDTO> organizationDTOS = new ArrayList<>();
        Optional<Organization> childOptional = organizationRepository.findById(Long.valueOf(childId));
        if (!childOptional.isPresent()) {
            throw new NoSuchDataException(childId);
        }
        Organization parent = null;
        Organization child = childOptional.get();
        if (parentId != null) {
            Optional<Organization> parentOptional = organizationRepository.findById(Long.valueOf(parentId));
            if (!parentOptional.isPresent()) {
                throw new NoSuchDataException(parentId);
            }
            parent = parentOptional.get();
        }
        if (parent != null) {
            if (child.getParent() != null) {
                child.getParent().getChildren().remove(child);
            }
            parent.getChildren().add(child);
            child.setParent(parent);
        } else {
            if (child.getParent() != null) {
                child.getParent().getChildren().remove(child);
            }
            child.setParent(null);
        }
        if (ids != null) {
            sortOrganizationByOrganizationIds(parent, ids, organizationDTOS);
        }

        this.updateTreePathAndLevel(child);
        return organizationDTOS;
    }

    /**
     * 设置组织顺序，parent不为空设置子组织顺序，parent为空则设置父组织顺序
     *
     * @param parent           父组织对象
     * @param ids              有序ID集合
     * @param organizationDTOS 返回的DTO集合
     */
    private void sortOrganizationByOrganizationIds(Organization parent, List<String> ids, List<OrganizationDTO> organizationDTOS) {
        int showOrder = 0;
        Map<String, Organization> map = new HashMap<>(ids.size());
        if (parent != null) {
            if (parent.getChildren() != null && !parent.getChildren().isEmpty()) {
                parent.getChildren().forEach(organization -> map.put(organization.getId().toString(), organization));
            }
            for (String id : ids) {
                if (map.get(id) != null) {
                    map.get(id).setShowOrder(++showOrder);
                    switch (map.get(id).getOrganizationType()) {
                        case UNIT:
                            map.get(id).getUnit().setShowOrder(showOrder);
                            break;
                        case DEPARTMENT:
                            map.get(id).getDepartment().setShowOrder(showOrder);
                            break;
                        case POSITION:
                            map.get(id).getPosition().setShowOrder(showOrder);
                            break;
                        default:
                            break;
                    }
                    organizationDTOS.add(organizationMapper.toDTO(map.get(id), new CycleAvoidingMappingContext()));
                }
            }
        } else {
            List<Organization> orgs = organizationRepository.findAll(Specifications.<Organization>and()
                    .eq("parent.id", null)
                    .build());
            orgs.forEach(organization -> map.put(organization.getId().toString(), organization));
            for (String id : ids) {
                if (map.get(id) != null) {
                    map.get(id).setShowOrder(++showOrder);
                    organizationDTOS.add(organizationMapper.toDTO(map.get(id), new CycleAvoidingMappingContext()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public Organization updateTreePathAndLevel(Organization organization) {
        Organization toBeSaved;
        if (organization.getId() == null) {
            toBeSaved = organizationRepository.save(organization);
        } else {
            toBeSaved = organization;
        }
        TreeUtil.calculatePathAndLevel(toBeSaved);
        if (toBeSaved.getChildren() != null && !toBeSaved.getChildren().isEmpty()) {
            toBeSaved.getChildren().forEach(this::updateChildrenTreePath);
        }
        return organizationRepository.save(toBeSaved);
    }

    /**
     * 递归更新子组织的TreePath
     *
     * @param organization 组织对象
     */
    private void updateChildrenTreePath(Organization organization) {
        TreeUtil.calculatePathAndLevel(organization);
        if (organization.getChildren() != null && !organization.getChildren().isEmpty()) {
            organization.getChildren().forEach(this::updateChildrenTreePath);
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PageDataDTO<EmployeeDTO> getEmployees(int page, int size, String sort, OrganizationQueryOptions organizationQueryOptions) {
        String organizationId = organizationQueryOptions.getOrganizationId();
        Optional<Organization> organization = organizationRepository.findById(Long.valueOf(organizationId));
        Boolean available = organizationQueryOptions.getAvailable();
        if (!organization.isPresent()) {
            throw new NoSuchDataException(organizationId);
        }
        String name = organizationQueryOptions.getName();
        if (name == null) {
            name = "%%";
        } else {
            name = "%" + name + "%";
        }
        String account = organizationQueryOptions.getAccount();
        if (account == null) {
            account = "%%";
        } else {
            account = "%" + account + "%";
        }
        Boolean deepLoad = organizationQueryOptions.getDeepLoad();
        // 此处换成其他类型均报类型转换异常
        List<Long> employeeIds;
        if (deepLoad != null && deepLoad) {
            if (available == null) {
                employeeIds = employeeRepository.findEmployeeIdByOrganization(organization.get().getTreePath() + "%", name, account);
            } else {
                employeeIds = employeeRepository.findEmployeeIdByOrganization(organization.get().getTreePath() + "%", name, account, available);
            }
        } else {
            if (available == null) {
                employeeIds = employeeRepository.findEmployeesByOrganizationId(organization.get().getId(), name, account);
            } else {
                employeeIds = employeeRepository.findEmployeesByOrganizationId(organization.get().getId(), name, account, available);
            }
        }

        Map<Integer, Long> employeeIdMap = new HashMap<>(16);

        List<Long> employeeIdsLong = new ArrayList<>();

        // 将数据保持顺序并去重
        this.sortEmployeeIds(employeeIdMap, employeeIds);

        for (int i = 0; i < employeeIds.size(); i++) {
            if (employeeIdMap.get(i) != null) {
                employeeIdsLong.add(employeeIdMap.get(i));
            }
        }

        Map<Long, Employee> map = new HashMap<>(employeeIds.size());
        PageDataDTO<EmployeeDTO> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setTotalPages(employeeIds.size() % size == 0 ? employeeIds.size() / size : employeeIds.size() / size + 1);
        List<Long> empIds = new ArrayList<>();
        Map<Integer, Integer> mapPageNo = new HashMap<>(16);

        // 构建页码Map<页码，集合下标>
        if (page != 0) {
            for (int i = 1; i <= pageDataDTO.getTotalPages(); i++) {
                mapPageNo.put(i, size * (i + page));
            }
        }
        // 根据页码截取员工ID集合
        if (page == 0) {
            if (employeeIdsLong.size() < size) {
                empIds = employeeIdsLong.subList(0, employeeIdsLong.size());
            } else {
                empIds = employeeIdsLong.subList(0, size);
            }
        }
        if (mapPageNo.get(page) != null) {
            if (employeeIdsLong.size() < mapPageNo.get(page)) {
                empIds = employeeIdsLong.subList(size * page, employeeIdsLong.size());
            } else {
                empIds = employeeIdsLong.subList(size * page, mapPageNo.get(page));
            }
        }
        List<Employee> employees = new ArrayList<>();
        if (!empIds.isEmpty()) {
            // 根据截取出来的员工ID集合获取员工对象集合(employees)(无序)
            employees = employeeRepository.findByEmployeeIds(empIds);
        }
        List<Employee> employeesSorted = new ArrayList<>();
        // 构建员工对象Map<ID,Employee>
        employees.forEach(emp -> map.put(emp.getId(), emp));

        // 根据截取出来的有序员工ID集合(empIds)给员工对象集合排序
        for (Long empId : empIds) {
            if (map.get(empId) != null) {
                employeesSorted.add(map.get(empId));
            }
        }
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeesSorted.forEach(employee -> addEmployeeDTOS(employeeDTOS, employee));
        pageDataDTO.setData(employeeDTOS);
        pageDataDTO.setPageNo(page);
        pageDataDTO.setTotalRecords((long) employeeIdsLong.size());
        pageDataDTO.setPageSize(size);
        return pageDataDTO;
    }

    /**
     * 遍历员工对象设置员工DTO
     *
     * @param employeeDTOS 员工DTO集合
     * @param employee     员工对象
     */
    private void addEmployeeDTOS(List<EmployeeDTO> employeeDTOS, Employee employee) {
        EmployeeDTO employeeDTO = employeeMapper.toDTO(employee);
        if (employee.getUser() != null) {
            employeeDTO.setAssignedUser(userMapper.toBaseDTO(employee.getUser()));
        }
        employeeDTO.setAssignedPositions(this.updateEmployeeDTO(employee));
        employeeDTOS.add(employeeDTO);
    }

    /**
     * 为employeeDTO更新所属岗位(因employee对象中没有Position)
     *
     * @param employee 员工对象
     */
    private List<PositionDTO> updateEmployeeDTO(Employee employee) {
        List<PositionDTO> positionDTOList = new ArrayList<>();
        employee.getOrganizations().forEach(organization -> {
            PositionDTO position = positionMapper.toDTO(organization.getPosition());
            OrganizationDTO organizationDTO = organizationMapper.toDTO(organization, new CycleAvoidingMappingContext());
            position.setAssignedOrganization(organizationDTO);
            positionDTOList.add(position);
        });
        return positionDTOList;
    }

    /**
     * 用于将数据库查出来的有序员工ID,保持顺序并去重
     *
     * @param employeeIdMap 员工IDMap
     * @param emps          员工ID集合
     */
    private void sortEmployeeIds(Map<Integer, Long> employeeIdMap, List<Long> emps) {
        // 将Id按照顺序（以0，1，2，3...为key）放入Map中
        for (int i = 0; i < emps.size(); i++) {
            // 重复值的标识
            boolean flag = false;
            for (int j = 0; j < i; j++) {
                if (employeeIdMap.get(j) != null && emps.get(i).longValue() == employeeIdMap.get(j)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }
            employeeIdMap.put(i, emps.get(i));
        }
    }

    @Override
    public List<RoleDTO> getRoles(String id) {
        Optional<Organization> organization = organizationRepository.findById(Long.valueOf(id));
        if (!organization.isPresent()) {
            throw new NoSuchDataException(id);
        }
        List<RoleDTO> roleDTOList = null;
        if (organization.get().getRoles() != null) {
            roleDTOList = roleMapper.toListRoleDTO(new ArrayList<>(organization.get().getRoles()));
        }
        return roleDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void grantRoles(String id, String... data) {
        Optional<Organization> organization = organizationRepository.findById(Long.valueOf(id));
        if (!organization.isPresent()) {
            throw new NoSuchDataException(id);
        }
        if (!organization.get().getRoles().isEmpty()) {
            organization.get().getRoles().removeAll(organization.get().getRoles());
        }
        Arrays.stream(data).forEach(roleId -> {
            Optional<Role> role = roleRepository.findById(Long.valueOf(roleId));
            if (!role.isPresent()) {
                throw new NoSuchDataException(roleId);
            }
            organization.get().getRoles().add(role.get());
            role.get().getOrganizations().add(organization.get());
        });
        organizationRepository.saveAndFlush(organization.get());
    }


    /**
     * 递归检查组织类型，去除类型不相符的组织
     *
     * @param organizations    组织集合
     * @param exclusive        限制类型
     * @param organizationType 组织类型
     */
    private void filterType(Collection<Organization> organizations, boolean exclusive, OrganizationType
            organizationType) {

        if (organizationType != null) {

            if (exclusive) {
                organizations.removeIf(o -> !organizationType.equals(o.getOrganizationType()));
            }

            // 如果指定要单位类型，递归移除非单位类型
            if (OrganizationType.UNIT.equals(organizationType)) {
                organizations.removeIf(o -> !organizationType.equals(o.getOrganizationType()));
                organizations.forEach(organization -> {
                    if (organization.getChildren() != null) {
                        organization.getChildren().removeIf(o -> !organizationType.equals(o.getOrganizationType()));
                        if (!organization.getChildren().isEmpty()) {
                            filterType(organization.getChildren(), exclusive, organizationType);
                        }
                    }
                });
            }

            // 如果类型是部门，则除去同级中的岗位
            if (OrganizationType.DEPARTMENT.equals((organizationType))) {
                organizations.removeIf(o -> OrganizationType.POSITION.equals(o.getOrganizationType()));
                organizations.forEach(organization -> {
                    if (organization.getChildren() != null) {
                        organization.getChildren().removeIf(o -> OrganizationType.POSITION.equals(o.getOrganizationType()));
                        if (!organization.getChildren().isEmpty()) {
                            filterType(organization.getChildren(), exclusive, organizationType);
                        }
                    }
                });
            }
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<OrganizationDTO> getChildren(String parentId, Boolean deepLoad, Boolean exclusive, OrganizationType organizationType, Boolean self, Boolean interior, String name) {
        // 如果self为真，则会只读id为parentId的数据
        List<Organization> organizations = organizationRepository.findAll(Specifications.<Organization>and()
                .eq((self && parentId != null) ? "id" : "parent.id", parentId)
                .eq(interior != null, "interior", interior)
                .like(name != null && !"".equals(name) && !"null".equals(name), "name", "%" + name + "%")
                .build());

        // 不需要装入子组织的话，则移除子组织
        if (!deepLoad) {
            organizations.forEach(organization -> organization.setChildren(null));

            // 如果强制指定类型，则移除不同的类型
            if (exclusive && organizationType != null && !self) {
                organizations.removeIf(o -> !organizationType.equals(o.getOrganizationType()));
            }
        }

        // 检查子组织
        if (self) {
            if (!organizations.isEmpty()) {
                this.filterType(organizations.get(0).getChildren(), exclusive, organizationType);
            }
        } else {
            this.filterType(organizations, exclusive, organizationType);
        }

        List<OrganizationDTO> organizationDTOList = organizationMapper.toListDTO(organizations, new CycleAvoidingMappingContext());

        return this.orderList(organizationDTOList);
    }


    private List<OrganizationDTO> transitionOrgAddUser(Collection<Organization> organizations) {
        if (organizations == null) {
            return null;
        }

        List<OrganizationDTO> list = new ArrayList<>(organizations.size());

        for (Organization organization : organizations) {
            OrganizationDTO organizationDTO = organizationMapper.toDTO(organization, new CycleAvoidingMappingContext());
            if (OrganizationType.POSITION.equals(organization.getOrganizationType())) {
                Set<Employee> employees = organization.getEmployees();
                employees.forEach(o -> {
                    User user = o.getUser();
                    organizationDTO.getUserBaseDTOS().add(userMapper.toBaseDTO(user));
                });
            }
            list.add(organizationDTO);
            transitionOrgAddUser(organization.getChildren());
        }

        return list;
    }

    /**
     * 对组织结构排序， 按照OrganizationType: unit -> department -> position以及showOrder来排序
     *
     * @param organizationDTOList 要排序的列表
     * @return 排好序的列表
     */
    private List<OrganizationDTO> orderList(List<OrganizationDTO> organizationDTOList) {

        Comparator<OrganizationDTO> comparator = ((o1, o2) -> {

            if (o1.getOrganizationType().ordinal() == o2.getOrganizationType().ordinal()) {
                if (o1.getShowOrder() < o2.getShowOrder()) {
                    return -1;
                } else if (o1.getShowOrder().intValue() == o2.getShowOrder()) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return o1.getOrganizationType().ordinal() < o2.getOrganizationType().ordinal() ? -1 : 1;
            }

        });

        Comparator<OrganizationDTO> comparator1 = (Comparator.comparing(OrganizationDTO::getShowOrder));
        organizationDTOList.sort(comparator.thenComparing(comparator1));
        organizationDTOList.forEach(o -> {
            List<OrganizationDTO> childrenDTOList;
            if (o.getChildren() != null && !o.getChildren().isEmpty()) {
                childrenDTOList = orderList(new ArrayList<>(o.getChildren()));
                o.setChildren(childrenDTOList);

            }
        });
        return organizationDTOList;
    }


    @Override
    public OrganizationDTO findByOrgId(String id) {
        Optional<Organization> org = organizationRepository.findById(Long.valueOf(id));
        if (!org.isPresent()) {
            throw new NoSuchDataException(id);
        }
        return organizationMapper.toDTO(org.get(), new CycleAvoidingMappingContext());
    }

    @Override
    public Organization from(String name, String shortName, Boolean available, Boolean interior, Integer showOrder, OrganizationType type) {
        Organization org = new Organization();
        org.setName(name);
        org.setShortName(shortName);
        org.setAvailable(available);
        org.setInterior(interior);
        org.setShowOrder(showOrder);
        org.setOrganizationType(type);
        return org;
    }

    @Override
    @Cacheable(cacheNames = {"Organization"}, key = "#id + '_' + #organizationId + '_authorities'")
    @CacheEvict(cacheNames = {"Positions", "User"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Set<String> getOrganizationAuthorities(String id, String organizationId) {
        Set<String> workingOrganizations = new HashSet<>();
        Optional<User> o = userRepository.findById(Long.parseLong(id));
        if (!o.isPresent()) {
            throw new NoSuchDataException(organizationId);
        }
        // 用户权限
        o.get().getRoles().forEach(r -> workingOrganizations.addAll(roleService.getRoleAuthorities(r.getId())));

        // 设置当前选中的组织权限
        Optional<Organization> organization = organizationRepository.findById(Long.valueOf(organizationId));
        if (!organization.isPresent()) {
            throw new NoSuchDataException(organizationId);
        }
        Organization organization1 = organization.get();
        if (OrganizationType.UNIT.equals(organization1.getOrganizationType())) {
            List<Organization> organizations = this.getPositionByUserIdAndOrganizationId(id, organizationId);
            organizations.forEach(r -> workingOrganizations.addAll(roleService.getOrganizationAuthorities(r.getId())));
        } else {
            organization1.getRoles().forEach(r -> workingOrganizations.addAll(roleService.getOrganizationAuthorities(r.getId())));
        }
        CurrentUserUtil.getInstance().setWorkingOrganizationId(organizationId);

        return workingOrganizations;
    }

    @Override
    @Cacheable(cacheNames = {"Positions"}, key = "#userId + #organizationId + '_positions'")
    public List<PositionBaseDTO> getPositionBaseDtoByUserIdAndOrganizationId(String userId, String organizationId) {
        List<PositionBaseDTO> positionBaseDTOS = new ArrayList<>();

        List<Organization> positionOrg = this.getPositionByUserIdAndOrganizationId(userId, organizationId);
        positionOrg.forEach(r -> positionBaseDTOS.add(
                positionMapper.toBaseDTO(r.getPosition())
        ));
        return positionBaseDTOS;
    }

    /**
     * 根据用户id和组织id查出所有的岗位
     *
     * @param userId         用户id
     * @param organizationId 组织id
     * @return 岗位集合
     */
    private List<Organization> getPositionByUserIdAndOrganizationId(String userId, String organizationId) {
        List<Organization> positionOrg = new ArrayList<>();
        Optional<User> o = userRepository.findById(Long.parseLong(userId));
        if (o.isPresent()) {
            User user = o.get();
            Employee employee = user.getEmployee();
            if (employee != null) {
                Organization organization = this.findById(Long.parseLong(organizationId));
                if (OrganizationType.UNIT.equals(organization.getOrganizationType())) {
                    // 先查询出当前单位下的所有部门
                    List<Organization> departments = organizationRepository.findAll(
                            Specifications.<Organization>and().like("filterPath", organization.getFilterPath() + "%")
                                    .eq("organizationType", OrganizationType.DEPARTMENT)
                                    .build());

                    // 查询出所有部门下的岗位
                    Specification<Organization> specification = ((Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
                        List<Predicate> conditions = new ArrayList<>();
                        conditions.add(cb.and(cb.equal(root.get("organizationType").as(OrganizationType.class), OrganizationType.POSITION)));
                        List<Predicate> departmentList = new ArrayList<>();
                        for (Organization department : departments) {
                            departmentList.add(cb.like(root.get("filterPath").as(String.class), department.getFilterPath() + "%"));
                        }
                        if (!departmentList.isEmpty()) {
                            Predicate[] arrayDepart = new Predicate[departmentList.size()];
                            conditions.add(cb.or(departmentList.toArray(arrayDepart)));
                        }
                        Join<Organization, Employee> parentJoin = root.join("employees", JoinType.LEFT);
                        conditions.add(cb.and(cb.equal(parentJoin.get("id").as(Long.class), employee.getId())));

                        if (!conditions.isEmpty()) {
                            Predicate[] array = new Predicate[conditions.size()];
                            query.where(conditions.toArray(array));
                        }
                        return null;
                    });
                    positionOrg = organizationRepository.findAll(specification);
                } else if (OrganizationType.DEPARTMENT.equals(organization.getOrganizationType())) {
                    // 查询出所有部门下的岗位
                    Specification<Organization> specification = ((Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
                        List<Predicate> conditions = new ArrayList<>();
                        conditions.add(cb.and(cb.equal(root.get("organizationType").as(OrganizationType.class), OrganizationType.POSITION)));
                        conditions.add(cb.like(root.get("filterPath").as(String.class), organization.getFilterPath() + "%"));
                        Join<Organization, Employee> parentJoin = root.join("employees", JoinType.LEFT);
                        conditions.add(cb.and(cb.equal(parentJoin.get("id").as(Long.class), employee.getId())));

                        if (!conditions.isEmpty()) {
                            Predicate[] array = new Predicate[conditions.size()];
                            query.where(conditions.toArray(array));
                        }
                        return null;
                    });
                    positionOrg = organizationRepository.findAll(specification);
                } else {
                    positionOrg.add(organization);
                }
            } else {
                throw new NoSuchDataException(userId);
            }

        }
        return positionOrg;
    }

    @Override
    public UnitBaseDTO getUnitOrgId(String organizationId) {
        UnitBaseDTO unit = null;
        Optional<Organization> childOptional = organizationRepository.findById(Long.valueOf(organizationId));
        if (!childOptional.isPresent()) {
            throw new NoSuchDataException(organizationId);
        }
        Organization org = childOptional.get();
        while (org != null) {
            if (OrganizationType.UNIT.equals(org.getOrganizationType())) {
                unit = unitMapper.toBaseDTO(org.getUnit());
                break;
            }

            org = org.getParent();
        }
        return unit;
    }

    @Override
    public DepartmentBaseDTO getDepartmentByOrgId(String organizationId) {
        DepartmentBaseDTO department = null;
        Optional<Organization> childOptional = organizationRepository.findById(Long.valueOf(organizationId));
        if (!childOptional.isPresent()) {
            throw new NoSuchDataException(organizationId);
        }
        Organization org = childOptional.get();
        while (org != null) {
            if (OrganizationType.DEPARTMENT.equals(org.getOrganizationType())) {
                department = departmentMapper.toBaseDTO(org.getDepartment());
                break;
            }

            org = org.getParent();
        }
        return department;
    }

    @Override
    public PositionBaseDTO getPositionOrgId(String organizationId) {
        PositionBaseDTO positionBaseDTO = null;
        Optional<Organization> childOptional = organizationRepository.findById(Long.parseLong(organizationId));
        if (!childOptional.isPresent()) {
            throw new NoSuchDataException(organizationId);
        }
        Organization org = childOptional.get();
        while (org != null) {
            if (OrganizationType.POSITION.equals(org.getOrganizationType())) {
                positionBaseDTO = positionMapper.toBaseDTO(org.getPosition());
                break;
            }

            org = org.getParent();
        }
        return positionBaseDTO;
    }

    @Override
    @CacheEvict(cacheNames = {"Positions"}, allEntries = true)
    public Map<String, String> getUnitAndDepartmentAndPositionFilterPathById(String organizationId) {
        Map<String, String> map = new HashMap<>(16);
        String unitPath = "";
        String departmentPath = "";
        String positionPath = "";

        // 获取用户的org的路径
        Optional<Organization> optional = organizationRepository.findById(Long.valueOf(organizationId));
        if (!optional.isPresent()) {
            throw new NoSuchDataException(organizationId);
        }
        Organization org = optional.get();
        while (org != null) {
            switch (org.getOrganizationType()) {
                case UNIT:
                    if (unitPath.isEmpty()) {
                        unitPath = org.getUnit().getFilterPath();
                    }
                    break;
                case DEPARTMENT:
                    if (departmentPath.isEmpty()) {
                        departmentPath = org.getDepartment().getFilterPath();
                    }
                    break;
                case POSITION:
                    if (positionPath.isEmpty()) {
                        positionPath = org.getPosition().getFilterPath();
                    }
                    break;
                default:
                    break;
            }
            org = org.getParent();
        }
        map.put("UNIT", unitPath);
        map.put("DEPARTMENT", departmentPath);
        map.put("POSITION", positionPath);
        return map;
    }

    @Override
    public Set<OrganizationDTO> findByOrganizationIdAndOrganizationType(String organizationId, OrganizationType organizationType) {
        Organization organization = organizationRepository.findById(parseLong(organizationId)).orElseThrow(() -> new NoSuchDataException(organizationId));
        return organizationMapper.toSetDTO(organizationRepository.findByTreePathAndOrganizationType(organization.getTreePath() + "%", organizationType)
                , new CycleAvoidingMappingContext());
    }

    @Override
    public Set<String> getCurrentOrgFilterPath(Organization organization) {
        Set<String> stringSet = new HashSet<>();
        // 获得当前组织下所有的organization
        Set<OrganizationDTO> organizationDTOS = organizationMapper.toSetDTO(organizationRepository.findByFilterPath("%" + organization.getFilterPath() + "%"), new CycleAvoidingMappingContext());
        // 如果是单位 则查询出当前单位所有子单位的组织
        if (OrganizationType.UNIT.equals(organization.getOrganizationType())) {
            Set<OrganizationDTO> childrenDto = organizationMapper.toSetDTO(organizationRepository.findChildrenById(organization.getId(), OrganizationType.UNIT), new CycleAvoidingMappingContext());
            for (OrganizationDTO organizationDTO : organizationDTOS) {
                String filterPath = organizationDTO.getFilterPath();
                for (OrganizationDTO children : childrenDto) {
                    String childrenFilterPath = children.getFilterPath();
                    if (!filterPath.contains(childrenFilterPath)) {
                        stringSet.add(filterPath);
                    }
                }
            }
        } else {
            for (OrganizationDTO organizationDTO : organizationDTOS) {
                stringSet.add(organizationDTO.getFilterPath());
            }
        }
        return stringSet;

    }

    @Override
    public OrganizationDTO getRecentlyOrganization(String organizationId, OrganizationType organizationType) {

        OrganizationDTO organizationUnitDTO = null;
        OrganizationDTO organizationDepartmentDTO = null;
        OrganizationDTO organizationPositionDTO = null;
        Organization org = organizationRepository.getOne(Long.valueOf(organizationId));
        while (org != null) {
            switch (org.getOrganizationType()) {
                case UNIT:
                    if (organizationUnitDTO == null) {
                        organizationUnitDTO = organizationMapper.toDTO(org, new CycleAvoidingMappingContext());
                    }
                    break;
                case DEPARTMENT:
                    if (organizationDepartmentDTO == null) {
                        organizationDepartmentDTO = organizationMapper.toDTO(org, new CycleAvoidingMappingContext());
                    }
                    break;
                case POSITION:
                    if (organizationPositionDTO == null) {
                        organizationPositionDTO = organizationMapper.toDTO(org, new CycleAvoidingMappingContext());
                    }
                    break;
                default:
                    break;
            }
            org = org.getParent();
        }

        if (OrganizationType.DEPARTMENT.equals(organizationType)) {
            return organizationDepartmentDTO;
        } else if (OrganizationType.POSITION.equals(organizationType)) {
            return organizationPositionDTO;
        } else if (OrganizationType.UNIT.equals(organizationType)) {
            return organizationUnitDTO;
        }

        return null;
    }

    @Override
    @CacheEvict(cacheNames = {"User"}, allEntries = true)
    public String getWorkingOrganizationByUserIdAndOrganizationType(String userId, OrganizationType organizationType) {
        Optional<User> o = userRepository.findById(Long.parseLong(userId));
        if (!o.isPresent()) {
            throw new NoSuchDataException(userId);
        }
        User user = o.get();
        Employee employee = user.getEmployee();
        if (employee != null) {
            Long defaultOrganizationId = employee.getDefaultOrganizationId();
            if (defaultOrganizationId == null) {
                Set<Organization> organizations = employee.getOrganizations();
                Iterator<Organization> iterator = organizations.iterator();
                if (iterator.hasNext()) {
                    Organization organization = iterator.next();
                    defaultOrganizationId = organization.getId();
                }
            }

            String unitId = "";
            String departmentId = "";
            String positionId = "";
            Organization org = organizationRepository.getOne(defaultOrganizationId);

            while (org != null) {
                switch (org.getOrganizationType()) {
                    case UNIT:
                        if (unitId.isEmpty()) {
                            unitId = org.getId().toString();
                        }
                        break;
                    case DEPARTMENT:
                        if (departmentId.isEmpty()) {
                            departmentId = org.getId().toString();
                        }
                        break;
                    case POSITION:
                        if (positionId.isEmpty()) {
                            positionId = org.getId().toString();
                        }
                        break;
                    default:
                        break;
                }
                org = org.getParent();
            }

            if (OrganizationType.UNIT.equals(organizationType)) {
                return unitId;
            } else if (OrganizationType.DEPARTMENT.equals(organizationType)) {
                return departmentId;
            } else if (OrganizationType.POSITION.equals(organizationType)) {
                return positionId;
            }
        }
        return "";
    }

    @Override
    public String getFilterPathByUserId(String userId, OrganizationType organizationType) {
        Optional<User> o = userRepository.findById(Long.parseLong(userId));
        if (!o.isPresent()) {
            throw new NoSuchDataException(userId);
        }
        User user = o.get();
        Employee employee = user.getEmployee();
        if (employee != null) {
            Long defaultOrganizationId = employee.getDefaultOrganizationId();
            if (defaultOrganizationId == null) {
                Iterator<Organization> iterator = employee.getOrganizations().iterator();
                if (iterator.hasNext()) {
                    Organization organization = iterator.next();
                    defaultOrganizationId = organization.getId();
                }
            }

            Map<String, String> map = this.getUnitAndDepartmentAndPositionFilterPathById(defaultOrganizationId.toString());
            if (OrganizationType.UNIT.equals(organizationType)) {
                return map.get("UNIT");
            } else if (OrganizationType.DEPARTMENT.equals(organizationType)) {
                return map.get("DEPARTMENT");
            } else if (OrganizationType.POSITION.equals(organizationType)) {
                return map.get("POSITION");
            }
        }

        return "";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SimpleTreeDataDTO> findTree(String[] employIds, String selectedOrgId, String type, String[] organzitionIds) {
        // 按照顶级组织机构分组
        String postiton = "POSITION";
        List<Organization> organizations = organizationRepository.findTop();
        // 由所有的组织架构记录组成的集合
        List<OrganizationDTO> organizationDTOList = organizationMapper.toListDTO(organizations, new CycleAvoidingMappingContext());
        Map<String, List<SimpleTreeDataDTO>> map = new HashMap<>(36);
        organizations = organizationRepository.findNotTop();
        organizationDTOList.addAll(organizationMapper.toListDTO(organizations, new CycleAvoidingMappingContext()));
        List<SimpleTreeDataDTO> result = new ArrayList<>();
        // 判断加载人员类型，是员工还是用户
        boolean loadType = "employee".equals(type);
        Set<Position> positionSet = new HashSet<>();
        // 延时加载的组织机构ID
        List<SimpleTreeDataDTO> selectedList;
        selectedList = this.findCurrentOrChildrenSimpleTreeById(positionSet, false, selectedOrgId, loadType);
        selectedList.forEach(selected -> {
            if (postiton.equals(selected.getType())) {

                this.findCurrentOrChildrenSimpleTreeById(positionSet, true, selected.getId(), loadType);
                // 人员
                List<SimpleTreeDataDTO> peopleChildrens = this.getPeopleByPostionId(selected.getId(), loadType);
                // 子岗位
                peopleChildrens.addAll(this.findCurrentOrChildrenSimpleTreeById(positionSet, true, selected.getId(), loadType));
                selected.setChildren(peopleChildrens);
            } else {
                selected.setChildren(this.findCurrentOrChildrenSimpleTreeById(positionSet, true, selected.getId(), loadType));
            }
        });
        // 根据组织机构id数组，获取组织机构集合。返回整棵树
        List<SimpleTreeDataDTO> orgList = new ArrayList<>();
        for (String org : organzitionIds) {
            if (org != null) {
                Long orgLong;
                try {
                    orgLong = Long.valueOf(org);
                    Organization tempOrg = this.findById(orgLong);
                    if (tempOrg.getPosition() != null) {
                        positionSet.add(tempOrg.getPosition());
                    }
                    orgList.addAll(this.findCurrentOrChildrenSimpleTreeById(positionSet, false, org, loadType));
                    orgList.forEach(temp -> {
                        List<SimpleTreeDataDTO> childrens = temp.getChildren();
                        if (childrens != null && !childrens.isEmpty()) {
                            childrens.addAll(this.findCurrentOrChildrenSimpleTreeById(positionSet, true, org, loadType));
                        } else {
                            childrens = this.findCurrentOrChildrenSimpleTreeById(positionSet, true, org, loadType);
                        }
                        temp.setChildren(childrens);
                    });
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        // 组织机构树，同一顶级的放在同一集合中，即是map的key为顶级的组织机构id，value为集合
        for (SimpleTreeDataDTO temp : orgList) {
            SimpleTreeDataDTO treeDataDTO = this.getPeopleTree(organizationDTOList, temp);
            List<SimpleTreeDataDTO> mapList = map.get(treeDataDTO.getId());
            if (mapList == null) {
                mapList = new ArrayList<>();
            }
            mapList.add(treeDataDTO);
            map.put(treeDataDTO.getId(), mapList);
        }
        // 根据员工或者用户id数组找到所有的岗位
        positionSet.addAll(this.getPostionsByIds(employIds));
        if (!this.getPostionsByIds(employIds).isEmpty()) {
            List<SimpleTreeDataDTO> positionList = new ArrayList<>();
            for (Position position : positionSet) {
                positionList.addAll(this.findCurrentOrChildrenSimpleTreeById(positionSet, false, position.getOrganization().getId().toString(), loadType));
            }
            for (SimpleTreeDataDTO temp : positionList) {
                SimpleTreeDataDTO treeDataDTO = this.getPeopleTree(organizationDTOList, temp);
                List<SimpleTreeDataDTO> mapList = map.get(treeDataDTO.getId());
                if (mapList != null) {
                    for (SimpleTreeDataDTO target : mapList) {
                        treeDataDTO = this.replaceSimpleTree(treeDataDTO, target);
                    }
                } else {
                    mapList = new ArrayList<>();
                }
                mapList.add(treeDataDTO);
                map.put(treeDataDTO.getId(), mapList);
            }
        }
        // map的key为顶级的组织机构id，value为集合，同一顶级的树，进行合并
        for (String key : map.keySet()) {
            List<SimpleTreeDataDTO> treeDataDTOList = map.get(key);
            SimpleTreeDataDTO temp = null;
            if (treeDataDTOList.size() > 0) {
                temp = treeDataDTOList.get(0);
            }
            if (treeDataDTOList.size() > 1) {
                for (int i = 1; i < treeDataDTOList.size(); i++) {
                    SimpleTreeDataDTO target = treeDataDTOList.get(i);
                    SimpleTreeDataDTO source = treeDataDTOList.get(i - 1);
                    temp = this.replaceSimpleTree(source, target);
                }
            }
            if (temp != null) {
                result.add(temp);
            }
        }
        if (result.isEmpty()) {
            return selectedList;
        } else {
            return result;
        }
    }


    /**
     * 根据组织机构ID获取组织机构集合，带有子节点的简单树
     *
     * @param positionSet   岗位集合，涉及员工所在岗位集合
     * @param swithces      查询当前组织机构的组织机构，false查询本机构， true查询子组织机构
     * @param organzitionId 组织机构ID
     * @param loadType      数据加载类型，true 为员工，false为用户
     * @return 组织机构树
     */
    private List<SimpleTreeDataDTO> findCurrentOrChildrenSimpleTreeById(Set<Position> positionSet, boolean swithces, String organzitionId, boolean loadType) {
        // 开关控制查询 条件  true 查询当前组织机构的组织机构， false查询子组织机构
        String config;

        if (organzitionId == null || "".equals(organzitionId)) {
            config = "parent.id";
        } else {
            config = "id";
        }
        if (swithces) {
            config = "parent.id";
        }

        OrganizationType organizationType = OrganizationType.POSITION;
        List<SimpleTreeDataDTO> result = new ArrayList<>();
        try {
            if (organzitionId != null) {
                Long.valueOf(organzitionId);
            }
        } catch (Exception e) {
            return result;
        }
        List<Organization> organizations = organizationRepository.findAll(Specifications.<Organization>and().eq(config, (organzitionId == null || "".equals(organzitionId)) ? null : organzitionId).build());
        if (organizations.isEmpty()) {
            return result;
        }
        for (Organization organization : organizations) {
            // 需要设置 children 为null ，表示打开状态，所以不能直接使用mapper转换的数据
            SimpleTreeDataDTO dataDTO = organizationTreeMapper.toDTO(organization, new CycleAvoidingMappingContext());
            // 重新设置children
            dataDTO.setChildren(null);
            dataDTO.setIsLeaf(false);
            // 判断组织机构类型
            // 如果是岗位
            if (organization.getOrganizationType() == organizationType) {
                dataDTO.setIsLeaf(false);
                // 岗位下的人员需要查询
                // 由于getEmlployeeList 需要查询数据库。 查询岗位下的员工，不仅类型是岗位，并且岗位的id与员工的岗位集合作比较
                if (positionSet != null && !positionSet.isEmpty()) {
                    positionSet.forEach(position -> {
                        if (organization.getId().equals(position.getOrganization().getId())) {
                            dataDTO.setChildren(this.getPeopleByPostionId(organzitionId, loadType));
                        }
                    });
                }
            }
            result.add(dataDTO);
        }
        return result;
    }

    /**
     * 根据岗位ID获取岗位和岗位下的员工
     */
    private List<SimpleTreeDataDTO> getPeopleByPostionId(String postionId, boolean loadType) {
        List<Organization> organizations = organizationRepository.findAll(Specifications.<Organization>and().eq("id", postionId).build());
        List<SimpleTreeDataDTO> result = new ArrayList<>();
        if (organizations.isEmpty()) {
            return result;
        }

        for (Organization organization : organizations) {
            // 需要设置 children 为null ，表示打开状态，所以不能直接使用mapper转换的数据
            if (organization.getOrganizationType() == OrganizationType.POSITION) {
                // 岗位下的人员需要查询
                List<SimpleTreeDataDTO> dto = new ArrayList<>();
                // 由于getEmlployeeList 需要查询数据库。 查询岗位下的员工，不仅类型是岗位，并且岗位的id与员工的岗位集合作比较
                List<EmployeeDTO> employeeDTOS = this.getEmployeeList(postionId, false);
                employeeDTOS.forEach(emp -> {
                    SimpleTreeDataDTO temp = new SimpleTreeDataDTO();
                    if (loadType) {
                        temp.setId(emp.getId());
                        temp.setName(emp.getName());
                        temp.setIsLeaf(true);
                        temp.setType("EMPLOYEE");
                        temp.setChildren(null);
                    } else {
                        if (emp.getAssignedUser() != null) {
                            Optional<User> user = userRepository.findById(Long.valueOf(emp.getAssignedUser().getId()));
                            if (user.isPresent()) {
                                temp.setId(user.get().getId().toString());
                                temp.setName(user.get().getName());
                                temp.setIsLeaf(true);
                                temp.setType("USER");
                                temp.setChildren(null);
                            }
                        }
                    }
                    if (temp.getId() != null) {
                        dto.add(temp);
                    }
                });
                // 重新设置children
                if (!dto.isEmpty()) {
                    result.addAll(dto);
                }
            }
        }
        return result;

    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<EmployeeDTO> getEmployeeList(String organizationId, Boolean deepLoad) {
        Optional<Organization> organization = organizationRepository.findById(Long.valueOf(organizationId));
        if (!organization.isPresent()) {
            throw new NoSuchDataException(organizationId);
        }
        List<Long> emps;
        if (deepLoad != null && deepLoad) {
            emps = employeeRepository.findEmployeeIdByOrganization(organization.get().getTreePath() + "%", "%%", "%%");
        } else {
            emps = employeeRepository.findEmployeesByOrganizationId(organization.get().getId(), "%%", "%%");
        }

        List<Long> empIds = new ArrayList<>();

        Map<Integer, Long> employeeIdMap = new HashMap<>(16);

        // 将数据库查出来的有序员工ID,保持顺序并去重
        this.sortEmployeeIds(employeeIdMap, emps);

        // 按照顺序，从Map中取出数据放入empIds集合中，此时数据的排序去重操作已经完成
        for (int i = 0; i < emps.size(); i++) {
            if (employeeIdMap.get(i) != null) {
                empIds.add(employeeIdMap.get(i));
            }
        }
        List<Employee> employees = new ArrayList<>();
        if (!empIds.isEmpty()) {
            // 将员工ID集合(empIds)放入方法中，找出ID集合(empIds)中所有员工对象
            employees = employeeRepository.findByEmployeeIds(empIds);
        }
        // 构建Map<员工ID,员工对象>
        Map<Long, Employee> employeeMap = new HashMap<>(empIds.size());
        // 放入数据
        employees.forEach(employee -> employeeMap.put(employee.getId(), employee));

        List<Employee> employeeList = new ArrayList<>();
        // 根据有序的员工ID集合(empIds),有序地查找出Map中的员工对象，放入(employeeList)中，
        // 此时员工对象有序集合完成
        empIds.forEach(e -> {
            if (employeeMap.get(e) != null) {
                employeeList.add(employeeMap.get(e));
            }
        });

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeeList.forEach(employee -> addEmployeeDTOS(employeeDTOS, employee));
        return employeeDTOS;
    }

    /**
     * 由人员的岗位的简单树，递归查找整棵树
     *
     * @param list    组织架构树的所有map记录
     * @param current 带有人员的岗位简单树
     * @return 返回岗位的关联组织机构的同级机构children为空的展开树
     */
    private SimpleTreeDataDTO getPeopleTree(List<OrganizationDTO> list, SimpleTreeDataDTO current) {
        // 保存所有同级组织机构
        List<SimpleTreeDataDTO> currents = new ArrayList<>();
        SimpleTreeDataDTO parent = new SimpleTreeDataDTO();
        String parentId = null;
        for (OrganizationDTO temp : list) {
            if (temp.getId().equals(current.getId())) {
                if (temp.getParent() != null && temp.getParent().getId() != null) {
                    parentId = temp.getParent().getId();
                }
                // 找到本组织机构
                currents.add(current);
            }
        }
        for (OrganizationDTO temp : list) {
            // 找到同级的其他组织机构
            if (parentId == null && temp.getParent() != null && temp.getParent().getId() != null) {
                if (!temp.getId().equals(current.getId())) {
                    temp.setChildren(null);
                    SimpleTreeDataDTO treeDataDTO = organizationTreeMapper.toDTO(temp, new CycleAvoidingMappingContext());
                    treeDataDTO.setIsLeaf(false);
                    currents.add(treeDataDTO);
                }
            } else {
                if (parentId != null && temp.getParent() != null) {
                    if (parentId.equals(temp.getParent().getId()) && !temp.getId().equals(current.getId())) {
                        temp.setChildren(null);
                        SimpleTreeDataDTO treeDataDTO = organizationTreeMapper.toDTO(temp, new CycleAvoidingMappingContext());
                        treeDataDTO.setIsLeaf(false);
                        currents.add(treeDataDTO);
                    }
                }
            }
        }
        // 排序
        List<SimpleTreeDataDTO> sortCurrents = this.sortSimpleTree(currents);
        // 如果本组织机构的parentId为空，说明本组织机构的同级组织机构都是顶级组织机构
        if (parentId == null) {
            return current;
        }
        //查找父组织结构
        for (OrganizationDTO temp : list) {
            if (parentId.equals(temp.getId())) {
                temp.setChildren(null);
                parent = organizationTreeMapper.toDTO(temp, new CycleAvoidingMappingContext());
                parent.setIsLeaf(false);
            }
        }
        if (parent.getId() != null) {
            parent.setChildren(sortCurrents);
            return this.getPeopleTree(list, parent);
        } else {
            return current;
        }
    }


    private List<SimpleTreeDataDTO> sortSimpleTree(List<SimpleTreeDataDTO> list) {
        //排序
        SimpleTreeDataDTO[] objs = new SimpleTreeDataDTO[list.size()];
        for (int i = 0; i < list.size(); i++) {
            objs[i] = list.get(i);
        }
        if (objs.length > 1) {
            for (int i = 0; i < objs.length - 1; i++) {
                for (int j = i + 1; j < objs.length; j++) {
                    if (Long.valueOf(objs[i].getId()) - Long.valueOf(objs[j].getId()) > 0) {
                        SimpleTreeDataDTO temp = objs[i];
                        objs[i] = objs[j];
                        objs[j] = temp;
                    }
                }
            }
        }
        list.clear();
        for (int i = 0; i < objs.length; i++) {
            list.add(i, objs[i]);
        }
        return list;
    }

    /**
     * 根据员工id数组获取员工所属所有岗位集合
     *
     * @param employIds 员工的id数组或者用户id数组
     * @return 员工所在岗位
     */
    private Set<Position> getPostionsByIds(String[] employIds) {
        // 获取所有员工的岗位
        Set<Position> positionSet = new LinkedHashSet<>();
        Optional<Employee> employee;

        //  根据人员ID数组判断是用户或者是员工的id ，所属的岗位
        // 获取员工ID数组的岗位数组
        if (employIds != null) {
            for (String employId : employIds) {
                try {
                    employee = employeeRepository.findById(Long.valueOf(employId));
                    if (employee.isPresent() && employee.get().getOrganizations() != null) {
                        Set<Organization> organizations = employee.get().getOrganizations();
                        organizations.forEach(o -> {
                            if (o.getPosition() != null) {
                                positionSet.add(o.getPosition());
                            }
                        });
                    } else {
                        Optional<User> user = userRepository.findById(Long.valueOf(employId));
                        if (user.isPresent()) {
                            Employee employee1 = user.get().getEmployee();
                            employee = employeeRepository.findById(Long.valueOf(employee1.getId()));
                            if (employee.isPresent() && employee.get().getOrganizations() != null) {
                                employee.get().getOrganizations().forEach(organization -> positionSet.add(organization.getPosition()));
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        return positionSet;
    }

    /**
     * 树的合并
     *
     * @ source 需要合并的树
     * @ target 被合并的树
     */
    private SimpleTreeDataDTO replaceSimpleTree(SimpleTreeDataDTO source, SimpleTreeDataDTO target) {

        // 判断是否相同
        boolean compareResut = this.compare(source, target);
        if (compareResut) {
            return source;
        } else {
            if (target == null) {
                return source;
            }
            List<SimpleTreeDataDTO> sourceChildren = source.getChildren();
            List<SimpleTreeDataDTO> targetChildren = target.getChildren();

            if (sourceChildren == null) {
                return target;
            }
            if (targetChildren == null) {
                return source;
            }

            // targetChildren 与 sourceChildren  都不为空
            List<SimpleTreeDataDTO> sortSourceChildren = this.sortSimpleTree(sourceChildren);
            List<SimpleTreeDataDTO> sortTargetChildren = this.sortSimpleTree(targetChildren);
            List<SimpleTreeDataDTO> resultChildren = new ArrayList<>();


            for (int i = 0; i < sortSourceChildren.size(); i++) {
                resultChildren.add(i, this.replaceSimpleTree(sortSourceChildren.get(i), sortTargetChildren.get(i)));
            }

            source.setChildren(resultChildren);
        }
        return source;
    }

    /**
     * 递归比较两颗树
     *
     * @param source 比较的树
     * @param target 目标树
     *               *
     */
    private boolean compare(SimpleTreeDataDTO source, SimpleTreeDataDTO target) {

        // 假定是相等的。如果下面遍历出的子节点不同，直接返回false，不相等
        try {
            if (source.getId().equals(target.getId())) {
                List<SimpleTreeDataDTO> sourceList = source.getChildren();
                List<SimpleTreeDataDTO> targetList = target.getChildren();
                if (sourceList == null) {
                    return false;
                }

                // 都不为空 ，需要递归判读
                for (int i = 0; i < sourceList.size(); i++) {
                    // 比较节点的子节点，如果一个字节点不相同，都返回false
                    for (int j = 0; j < targetList.size(); j++) {
                        boolean childenReuslt = compare(sourceList.get(i), targetList.get(i));
                        if (!childenReuslt) {
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<OrganizationDTO> findAll(String name, OrganizationType organizationType) {
        List<OrganizationDTO> organizationDTOS = new ArrayList<>();
        List<Organization> organizations = organizationRepository.findAll(Specifications.<Organization>and().like(name != null && !"".equals(name) && !"null".equals(name), "name", "%" + name + "%")
                .eq(organizationType != null, "organizationType", organizationType)
                .build());
        organizations.forEach(organization -> organizationDTOS.add(organizationMapper.toDTO(organization, new CycleAvoidingMappingContext())));
        return organizationDTOS;
    }

    @Override
    public PageDataDTO<OrganizationDTO> findOnePage(int page, int size, String sort, String name, OrganizationType organizationType) {
        List<OrganizationDTO> organizationDTOS = new ArrayList<>();
        Page<Organization> rolePage = organizationRepository.findAll(Specifications.<Organization>and().like(name != null && !"".equals(name) && !"null".equals(name), "name", "%" + name + "%")
                .eq(organizationType != null, "organizationType", organizationType)
                .build(), PageHelper.generatePageRequest(page, size, sort));
        rolePage.getContent().forEach(organization ->
                organizationDTOS.add(organizationMapper.toDTO(organization, new CycleAvoidingMappingContext()))
        );
        return PageDataUtil.toPageData(rolePage, organizationDTOS);
    }
}
