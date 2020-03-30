package com.kamixiya.icm.service.common.service.security;

import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.model.security.role.RoleDTO;
import com.kamixiya.icm.model.security.user.PasswordInfoDTO;
import com.kamixiya.icm.model.security.user.UserDTO;
import com.kamixiya.icm.model.security.user.UserEditInfoDTO;
import com.kamixiya.icm.service.common.entity.organization.Employee;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.security.Role;
import com.kamixiya.icm.service.common.entity.security.User;
import com.kamixiya.icm.service.common.exception.InvalidPasswordException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.filter.CurrentUserUtil;
import com.kamixiya.icm.service.common.mapper.organization.OrganizationMapper;
import com.kamixiya.icm.service.common.mapper.security.RoleMapper;
import com.kamixiya.icm.service.common.mapper.security.UserMapper;
import com.kamixiya.icm.service.common.repository.organization.EmployeeRepository;
import com.kamixiya.icm.service.common.repository.security.RoleRepository;
import com.kamixiya.icm.service.common.repository.security.UserRepository;
import com.kamixiya.icm.service.common.service.organization.OrganizationService;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * UserServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/3/13
 */
@Service("userService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl implements UserService{

    private static final String PASS_PLACE_HOLDER = "$pWd$(C%#*(78))!<>";
    private static final int MAX_TRY_TIMES = 5;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final OrganizationMapper organizationMapper;
    private final OrganizationService organizationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleService roleService, EmployeeRepository employeeRepository,
                           RoleRepository roleRepository, RoleMapper roleMapper, OrganizationMapper organizationMapper, OrganizationService organizationService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.organizationMapper = organizationMapper;
        this.organizationService = organizationService;
    }

    protected UserDTO customizeUser(UserDTO userDTO) {
        return userDTO;
    }

    @Override
    @Cacheable(cacheNames = {"User"}, key = "#id")
    public UserDTO findById(String id) {
        Optional<User> o = userRepository.findById(Long.parseLong(id));
        if (o.isPresent()) {
            UserDTO userDTO = userMapper.toDTO(o.get());
            userDTO.setAccountLocked(o.get().getWrongPasswordCount() >= MAX_TRY_TIMES);
            Set<String> authorities = new HashSet<>();
            // 用户权限
            o.get().getRoles().forEach(r -> authorities.addAll(roleService.getRoleAuthorities(r.getId())));
            Employee employee = o.get().getEmployee();
            // 给当前用户设置所拥有的组织
            if (employee != null) {
                Set<Organization> organizationSetOLd = employee.getOrganizations();
                // 设置默认岗位权限
                Long defaultOrganizationId = employee.getDefaultOrganizationId();
                if (defaultOrganizationId != null) {
                    authorities.addAll(roleService.getOrganizationAuthorities(defaultOrganizationId));
                } else {
                    // 岗位权限
                    organizationSetOLd.forEach(org -> authorities.addAll(roleService.getOrganizationAuthorities(org.getId())));
                }
                Set<OrganizationDTO> dtos = new HashSet<>(16);
                Map<Long, OrganizationDTO> dtoMap = new HashMap<>(16);
                Map<Long, Organization> selectMap = new HashMap<>(16);
                organizationSetOLd.forEach(organization -> {
                    if (organization.getChildren() != null) {
                        organization.getChildren().removeIf(Objects::nonNull);
                    }
                });
                OrganizationType organizationType = null;
                String workingOrganizationId = CurrentUserUtil.getInstance().getWorkingOrganizationId();
                if (workingOrganizationId != null && !"".equals(workingOrganizationId)) {
                    organizationType = organizationService.findById(Long.valueOf(workingOrganizationId)).getOrganizationType();
                    for (Organization org : organizationSetOLd) {
                        while (org != null) {
                            if (org.getOrganizationType().equals(organizationType)) {
                                selectMap.put(org.getId(), org);
                                break;
                            }
                            org = org.getParent();
                        }
                    }
                }
                for (Organization organization : organizationSetOLd) {
                    Organization organizationParent = organization.getParent();
                    OrganizationDTO self = organizationMapper.toDTO(organization, new CycleAvoidingMappingContext());
                    if (organizationParent == null) {
                        self.setChildren(null);
                        dtos.add(self);
                    } else {
                        OrganizationDTO topDto = recursionOrganizationParent(organizationParent, self, dtoMap, organizationType);
                        if (topDto != null) {
                            dtos.add(topDto);
                        }
                    }
                }

                if (OrganizationType.UNIT.equals(organizationType)) {
                    // 只保留单位
                    getOrganizationByOrganizationType(dtos, organizationType, OrganizationType.UNIT, selectMap);
                } else if (OrganizationType.DEPARTMENT.equals(organizationType)) {
                    // 剔除所有岗位
                    getOrganizationByOrganizationType(dtos, OrganizationType.POSITION, OrganizationType.DEPARTMENT, selectMap);
                } else {
                    // 赋值岗位那些可以选
                    getOrganizationByOrganizationType(dtos, OrganizationType.POSITION, OrganizationType.POSITION, selectMap);
                }
                userDTO.setTopOrganizationDTOS(dtos);


            }
            userDTO.setAuthorities(authorities);
            return customizeUser(userDTO);
        } else {
            throw new NoSuchDataException(id);
        }
    }

    /**
     * 剔除或者保留对应类型的组织
     *
     * @param dtos             组织集合
     * @param organizationType 剔除或者保存的组织类型
     * @param b                UNIT 是保留，DEPARTMENT 是剔除 ,POSITION不变
     * @param selectMap        可以选中的组织
     */
    private void getOrganizationByOrganizationType(Collection<OrganizationDTO> dtos, OrganizationType organizationType, OrganizationType b, Map<Long, Organization> selectMap) {
        Iterator<OrganizationDTO> dtoIterator = dtos.iterator();
        while (dtoIterator.hasNext()) {
            OrganizationDTO organizationDTO = dtoIterator.next();
            if (selectMap.get(Long.parseLong(organizationDTO.getId())) != null) {
                organizationDTO.setSelect(true);
            }
            List<OrganizationDTO> organizations = organizationDTO.getChildren();
            if (b.equals(OrganizationType.UNIT)) {
                if (organizationType.equals(organizationDTO.getOrganizationType())) {
                    if (organizations != null) {
                        getOrganizationByOrganizationType(organizations, organizationType, b, selectMap);
                    }
                } else {
                    dtoIterator.remove();
                }
            } else if (b.equals(OrganizationType.DEPARTMENT)) {
                if (!organizationType.equals(organizationDTO.getOrganizationType())) {
                    if (organizations != null) {
                        getOrganizationByOrganizationType(organizations, organizationType, b, selectMap);
                    }
                } else {
                    dtoIterator.remove();
                }
            }
        }


    }

    /**
     * 递归组织信息查询组合所有父级
     *
     * @param parent   父组织
     * @param childDto 当前组织dto
     * @param dtoMap   组织map（去掉重复的）
     * @return 组织dto
     */
    private OrganizationDTO recursionOrganizationParent(Organization parent, OrganizationDTO childDto, Map<Long, OrganizationDTO> dtoMap, OrganizationType organizationType) {
        OrganizationDTO parentDto = populateDTO(parent, childDto, dtoMap);
        if (parentDto == null) {
            return null;
        }
        if (parentDto.getOrganizationType().equals(organizationType)) {

        }
        if (parent.getParent() != null) {
            return recursionOrganizationParent(parent.getParent(), parentDto, dtoMap, organizationType);
        } else {
            return parentDto;
        }
    }

    /**
     * @param organization 组织信息
     * @param childDTO     组织dto
     * @param dtoMap       组织map（去掉重复的）
     * @return 组织dto
     */
    private OrganizationDTO populateDTO(Organization organization, OrganizationDTO childDTO, Map<Long, OrganizationDTO> dtoMap) {
        OrganizationDTO dto = dtoMap.get(organization.getId());
        if (dto == null) {
            dto = organizationMapper.toDTO(organization, new CycleAvoidingMappingContext());
            if (dto.getChildren() != null) {
                dto.getChildren().clear();
                dtoMap.put(organization.getId(), dto);
                dto.getChildren().add(childDTO);
            }
            return dto;
        } else {
            if (dto.getChildren() != null) {
                dto.getChildren().add(childDTO);
            }
            return null;
        }

    }

    @Override
    public PageDataDTO<UserDTO> findOnePage(int page, int size, String sort, String name) {
        PageRequest pr = PageHelper.generatePageRequest(page, size, sort);
        Page<User> users = userRepository.findAll(Specifications.<User>and().like(name != null && !"".equals(name) && !"null".equals(name), "name", "%" + name + "%").build(), pr);
        List<UserDTO> userDTOs = new ArrayList<>();
        List<User> userList = users.getContent();
        userList.forEach(user -> {
            UserDTO userDTO = userMapper.toDTO(user);
            userDTO.setAccountLocked(user.getWrongPasswordCount() >= MAX_TRY_TIMES);
            userDTOs.add(userDTO);
        });
        return PageDataUtil.toPageData(users, userDTOs);
    }

    @Override
    public List<UserDTO> findAll(String name) {
        List<User> users = userRepository.findAll(Specifications.<User>and().like(name != null && !"".equals(name) && !"null".equals(name), "name", "%" + name + "%").build());
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SimpleDataDTO<Boolean> checkAccountAvailability(String userId, String account) {
        String foundUserId = this.findByAccount(account);
        if (foundUserId == null) {
            return new SimpleDataDTO<>(true);
        } else {
            boolean rtn = userId != null && userId.equalsIgnoreCase(foundUserId);
            return new SimpleDataDTO<>(rtn);
        }
    }

    private Date calculatePasswordExpireDate() {
        // 暂时密码无有效期，永远有效
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"User"}, key = "#userId")
    public void changePassword(String userId, PasswordInfoDTO passwordInfoDTO) {
        Optional<User> o = userRepository.findById(Long.valueOf(userId));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (o.isPresent() && o.get().getLocalAccount()) {
            User user = o.get();
            String oldPwd = passwordInfoDTO.getOldPassword();
            String newPwd = passwordInfoDTO.getNewPassword();
            if (bCryptPasswordEncoder.matches(oldPwd, user.getPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(newPwd));
                user.setChangePasswordTime(new Date());
                user.setPasswordExpireDate(calculatePasswordExpireDate());
                user.setWrongPasswordCount(0);
                user.setLastWrongPasswordTime(null);
                userRepository.save(user);
            } else {
                throw new InvalidPasswordException("原密码不对，无权修改密码！");
            }
        } else {
            throw new NoSuchDataException(userId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(UserEditInfoDTO userEditInfoDTO) {

        if (log.isDebugEnabled()) {
            log.info("进入创建用户");
        }
        User user = userMapper.toEntity(userEditInfoDTO);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user = userRepository.saveAndFlush(user);
        if (log.isDebugEnabled()) {
            log.info("保存用户");
        }
        if (userEditInfoDTO.getEmployeeId() != null) {
            Optional<Employee> employee = employeeRepository.findById(Long.valueOf(userEditInfoDTO.getEmployeeId()));
            if (!employee.isPresent()) {
                throw new NoSuchDataException(userEditInfoDTO.getEmployeeId());
            }
            Employee emp = employee.get();
            user.setFilterPath(emp.getFilterPath());
            emp.setUser(user);
            emp = employeeRepository.saveAndFlush(emp);
            if (log.isDebugEnabled()) {
                log.info("有员工保存用户");
            }
            user.setEmployee(emp);
        }
        if (log.isDebugEnabled()) {
            log.info("离开创建用户");
        }
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"User"}, key = "#id")
    public UserDTO update(String id, UserEditInfoDTO userEditInfoDTO) {
        Optional<User> o = userRepository.findById(Long.valueOf(id));
        User user;
        if (o.isPresent()) {
            user = o.get();
            String oldPwd = o.get().getPassword();
            userMapper.updateEntity(userEditInfoDTO, user);

            if (!PASS_PLACE_HOLDER.equals(userEditInfoDTO.getPassword())) {
                user.setPassword(new BCryptPasswordEncoder().encode(userEditInfoDTO.getPassword()));
                user.setChangePasswordTime(new Date());
            } else {
                user.setPassword(oldPwd);
            }
            userRepository.save(user);
            UserDTO dto = userMapper.toDTO(user);
            dto.setPassword(PASS_PLACE_HOLDER);
            return dto;
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"User"}, key = "#id")
    public void delete(String id) {
        Optional<User> old = userRepository.findById(Long.valueOf(id));
        if (old.isPresent()) {
            // 解除用户与角色的关联
            old.get().getRoles().forEach(role -> {
                role.getUsers().remove(old.get());
                roleRepository.save(role);
            });
            Employee employee = old.get().getEmployee();
            if (employee != null) {
                old.get().getEmployee().setUser(null);
                old.get().setEmployee(null);
            }
            userRepository.delete(old.get());
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    public List<RoleDTO> getRoles(String id) {
        Optional<User> old = userRepository.findById(Long.valueOf(id));
        if (old.isPresent()) {
            return roleMapper.toListRoleDTO(new ArrayList<>(old.get().getRoles()));
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"User"}, key = "#id")
    public void grantRoles(String id, String[] data) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        if (!user.isPresent()) {
            throw new NoSuchDataException(id);
        }

        if (!user.get().getRoles().isEmpty()) {
            user.get().getRoles().removeAll(user.get().getRoles());
        }

        Arrays.stream(data).forEach(roleId -> {
            Optional<Role> role = roleRepository.findById(Long.valueOf(roleId));
            if (!role.isPresent()) {
                throw new NoSuchDataException(roleId);
            }
            role.get().getUsers().add(user.get());
            user.get().getRoles().add(role.get());
        });
        userRepository.save(user.get());

    }

    @Override
    public Collection<String> setWorkingOrganization(String organizationId) {
        return null;
    }

    @Override
    public String findByAccount(String account) {
        Long id = userRepository.findByAccount(account);
        if (id == null) {
            return null;
        } else {
            return String.valueOf(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"User"}, key = "#id")
    public void updateLoginCountOnAnotherDay(String id) {
        Optional<User> o = userRepository.findById(Long.valueOf(id));
        o.ifPresent(user -> {
            if (user.getLastWrongPasswordTime() != null) {
                if (!DateUtils.isSameDay(new Date(), user.getLastWrongPasswordTime())) {
                    user.setLastWrongPasswordTime(null);
                    user.setWrongPasswordCount(0);
                    userRepository.save(user);
                }
            }
        });
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"User"}, key = "#userId")
    public void loginFailed(String userId) {
        Optional<User> o = userRepository.findById(Long.valueOf(userId));
        o.ifPresent(u -> {
            u.setWrongPasswordCount(u.getWrongPasswordCount() + 1);
            u.setLastWrongPasswordTime(new Date());
            userRepository.save(u);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetLoginCountAfterSuccessfulLogin(String id) {
        Optional<User> o = userRepository.findById(Long.valueOf(id));
        o.ifPresent(user -> {
            user.setLastWrongPasswordTime(null);
            user.setWrongPasswordCount(0);
            userRepository.save(user);
        });
    }
}
