package com.kamixiya.icm.service.common.service.organization;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.organization.employee.EmployeeDTO;
import com.kamixiya.icm.model.organization.employee.EmployeeEditInfoDTO;
import com.kamixiya.icm.model.organization.position.PositionDTO;
import com.kamixiya.icm.service.common.entity.organization.Employee;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.organization.Position;
import com.kamixiya.icm.service.common.entity.security.User;
import com.kamixiya.icm.service.common.exception.DefaultOrganizationIdNoExistsPositionDataException;
import com.kamixiya.icm.service.common.exception.HasRelatedDataException;
import com.kamixiya.icm.service.common.exception.NoPositionDataException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.mapper.organization.EmployeeMapper;
import com.kamixiya.icm.service.common.mapper.organization.OrganizationMapper;
import com.kamixiya.icm.service.common.mapper.organization.PositionMapper;
import com.kamixiya.icm.service.common.mapper.security.UserMapper;
import com.kamixiya.icm.service.common.repository.organization.EmployeeRepository;
import com.kamixiya.icm.service.common.repository.organization.OrganizationRepository;
import com.kamixiya.icm.service.common.repository.organization.PositionRepository;
import com.kamixiya.icm.service.common.repository.security.UserRepository;
import com.kamixiya.icm.service.common.service.util.FilterPathUtil;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * EmployeeServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Slf4j
@Service("employeeService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrganizationMapper organizationMapper;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, OrganizationMapper organizationMapper,
                               PositionRepository positionRepository, PositionMapper positionMapper,
                               UserRepository userRepository, UserMapper userMapper, OrganizationRepository organizationRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.organizationMapper = organizationMapper;
        this.organizationRepository = organizationRepository;
    }


    @Override
    public EmployeeDTO findById(String id) {
        Optional<Employee> byId = employeeRepository.findById(Long.valueOf(id));
        if (!byId.isPresent()) {
            throw new NoSuchDataException(id);
        }
        Set<Position> positionSet = new LinkedHashSet<>();
        String defaultOrganizationId = null;
        if (byId.get().getOrganizations() != null) {
            byId.get().getOrganizations().forEach(organization -> positionSet.add(organization.getPosition()));
        }
        if (byId.get().getDefaultOrganizationId() != null) {
            Optional<Organization> optionalOrganization = organizationRepository.findById(byId.get().getDefaultOrganizationId());
            if (!optionalOrganization.isPresent()) {
                throw new NoSuchDataException(id);
            }
            defaultOrganizationId = optionalOrganization.get().getPosition().getId().toString();
        }
        EmployeeDTO employeeDTO = employeeMapper.toDTO(byId.get());
        employeeDTO.setAssignedPositions(positionMapper.toList(positionSet));
        employeeDTO.setDefaultOrganizationId(defaultOrganizationId);
        return employeeDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDTO create(EmployeeEditInfoDTO employeeEditInfoDTO) {
        Employee employee = employeeMapper.toEntity(employeeEditInfoDTO);
        List<PositionDTO> positions = null;
        // 如果岗位不为空
        List<String> positionIds = employeeEditInfoDTO.getPositionIds();
        if (positionIds != null) {
            Set<Organization> orgSet = new LinkedHashSet<>();
            if (positionIds.size() == 1 && employeeEditInfoDTO.getDefaultOrganizationId() == null) {
                employeeEditInfoDTO.setDefaultOrganizationId(positionIds.get(0));
            }
            boolean isPositionIds = false;
            for (String positionId : positionIds) {
                Optional<Position> position = positionRepository.findById(Long.valueOf(positionId));
                if (!position.isPresent()) {
                    throw new NoSuchDataException(positionId);
                }
                if (positionId.equals(employeeEditInfoDTO.getDefaultOrganizationId())) {
                    isPositionIds = true;
                    employee.setDefaultOrganizationId(position.get().getOrganization().getId());
                }
                orgSet.add(position.get().getOrganization());
            }
            if (!isPositionIds) {
                throw new DefaultOrganizationIdNoExistsPositionDataException();
            }
            employee.setOrganizations(orgSet);
        } else {
            throw new NoPositionDataException();
        }

        // 计算FilterPath
        if (!employee.getOrganizations().isEmpty()) {
            employee.setFilterPath(calculatePath(employee.getOrganizations()));
        }

        if (employeeEditInfoDTO.getPositionIds() != null) {
            positions = updatePosition(employeeEditInfoDTO.getPositionIds());
        }
        employee = employeeRepository.save(employee);

        // 为用户设置和员工一样的FilterPath
        if (employee.getUser() != null) {
            employee.getUser().setEmployee(employee);
            employee.getUser().setFilterPath(employee.getFilterPath());
        }

        // 给岗位设置员工
        if (employee.getOrganizations() != null) {
            for (Organization organization : employee.getOrganizations()) {
                organization.getEmployees().add(employee);
            }
        }
        EmployeeDTO employeeDTO = employeeMapper.toDTO(employee);
        employeeDTO.setAssignedPositions(positions);
        employeeDTO.setAssignedUser(userMapper.toDTO(employee.getUser()));
        employeeDTO.setDefaultOrganizationId(employeeEditInfoDTO.getDefaultOrganizationId());
        return employeeDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"User"}, allEntries = true)
    public EmployeeDTO update(String id, EmployeeEditInfoDTO employeeEditInfoDTO) {
        Optional<Employee> emp = employeeRepository.findById(Long.valueOf(id));
        if (!emp.isPresent()) {
            throw new NoSuchDataException(id);
        }
        Employee employee = emp.get();
        employeeMapper.updateEntity(employeeEditInfoDTO, employee);
        // 修改员工时修改岗位
        List<PositionDTO> positions = null;
        List<String> positionIds = employeeEditInfoDTO.getPositionIds();
        if (positionIds != null) {
            if (positionIds.size() == 1 && employeeEditInfoDTO.getDefaultOrganizationId() == null) {
                employeeEditInfoDTO.setDefaultOrganizationId(positionIds.get(0));
            }
            boolean isPositionIds = false;
            for (String positionId : positionIds) {
                if (positionId.equals(employeeEditInfoDTO.getDefaultOrganizationId())) {
                    Optional<Position> position = positionRepository.findById(Long.valueOf(positionId));
                    if (!position.isPresent()) {
                        throw new NoSuchDataException(positionId);
                    }
                    isPositionIds = true;
                    employee.setDefaultOrganizationId(position.get().getOrganization().getId());
                }
            }
            if (!isPositionIds) {
                throw new DefaultOrganizationIdNoExistsPositionDataException();
            }

            Set<Organization> orgSet = new LinkedHashSet<>();
            for (Organization o : employee.getOrganizations()) {
                o.getEmployees().remove(employee);
            }

            positions = updatePosition(employeeEditInfoDTO.getPositionIds());
            if (!positions.isEmpty()) {
                positions.forEach(positionDTO -> orgSet.add(organizationMapper.toEntity(positionDTO.getAssignedOrganization(), new CycleAvoidingMappingContext())));
            }

            employee.setOrganizations(orgSet);
        } else {
            throw new NoPositionDataException();
        }

        // 计算FilterPath
        if (!employee.getOrganizations().isEmpty()) {
            employee.setFilterPath(calculatePath(employee.getOrganizations()));
        }

        if (!employee.getOrganizations().isEmpty()) {
            positions = new ArrayList<>();
            for (Organization organization : employee.getOrganizations()) {
                positions.add(positionMapper.toDTO(organization.getPosition()));
            }
        }

        employee = employeeRepository.saveAndFlush(employee);
        EmployeeDTO employeeDTO = employeeMapper.toDTO(employee);
        employeeDTO.setAssignedPositions(positions);
        employeeDTO.setDefaultOrganizationId(employeeEditInfoDTO.getDefaultOrganizationId());
        return employeeDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Optional<Employee> byId = employeeRepository.findById(Long.valueOf(id));
        if (!byId.isPresent()) {
            throw new NoSuchDataException(id);
        }
        Employee employee = byId.get();
        employee.getOrganizations().forEach(organization -> organization.getEmployees().remove(employee));
        employeeRepository.delete(employee);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDTO bindUser(String employeeId, String userId) {
        Optional<Employee> employee = employeeRepository.findById(Long.valueOf(employeeId));
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if (!employee.isPresent() || !user.isPresent()) {
            throw new NoSuchDataException(employeeId + " or " + userId);
        }
        Employee emp = employee.get();
        if (emp.getUser() != null) {
            throw new HasRelatedDataException("员工" + employeeId);
        }

        if (user.get().getEmployee() != null) {
            throw new HasRelatedDataException("用户" + userId);
        }
        emp.setUser(user.get());
        user.get().setEmployee(emp);
        emp = employeeRepository.saveAndFlush(emp);
        EmployeeDTO employeeDTO = employeeMapper.toDTO(emp);
        if (emp.getUser() != null) {
            employeeDTO.setAssignedUser(userMapper.toDTO(emp.getUser()));
        }
        return employeeDTO;
    }

    /**
     * 岗位id集合转实体
     *
     * @param positionIds 岗位id集合
     * @return 岗位实体集合
     */
    private List<PositionDTO> updatePosition(List<String> positionIds) {
        List<Long> posIds = new ArrayList<>();
        List<PositionDTO> positionDTOS = new ArrayList<>();
        positionIds.forEach(pid -> posIds.add(Long.valueOf(pid)));
        List<Position> byPositionIds = positionRepository.findByPositionIds(posIds);
        if (byPositionIds.isEmpty()) {
            return new ArrayList<>();
        }
        byPositionIds.forEach(data -> {
            PositionDTO positionDTO = positionMapper.toDTO(data);
            positionDTO.setAssignedOrganization(organizationMapper.toDTO(data.getOrganization(), new CycleAvoidingMappingContext()));
            positionDTOS.add(positionDTO);
        });
        return positionDTOS;
    }


    /**
     * 计算FilterPath
     *
     * @param organizations 组织集合
     * @return FilterPath
     */
    private String calculatePath(Set<Organization> organizations) {
        StringBuilder filterPath = new StringBuilder();
        int tag = organizations.size() - 1;
        for (Organization org : organizations) {
            filterPath.append(FilterPathUtil.calculateOrganizationFilterPath(org));
            if (tag > 0) {
                filterPath.append(";");
            }
            tag--;
        }
        return filterPath.toString();
    }

    @Override
    public SimpleDataDTO<Boolean> checkCode(String id, String code) {
        Long unitId = employeeRepository.findEmployeeByCode(code);
        if (unitId == null) {
            return new SimpleDataDTO<>(true);
        } else {
            boolean rtn = id != null && id.equals(String.valueOf(unitId));
            return new SimpleDataDTO<>(rtn);
        }
    }

    @Override
    public List<EmployeeDTO> findAll(String sort, String name, Boolean available) {
        PredicateBuilder<Employee> predicateBuilder = Specifications.<Employee>and()
                .eq(available != null, "available", available)
                .like(StringUtils.isNotBlank(name), "name", "%" + name + "%");
        return employeeMapper.toListEntity(employeeRepository.findAll(predicateBuilder.build(), new Sort(Sort.Direction.ASC, sort)));
    }
}
