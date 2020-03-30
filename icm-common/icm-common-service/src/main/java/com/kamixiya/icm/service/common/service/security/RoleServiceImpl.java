package com.kamixiya.icm.service.common.service.security;

import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.security.authority.AuthorityBaseDTO;
import com.kamixiya.icm.model.security.authority.AuthorityDTO;
import com.kamixiya.icm.model.security.role.RoleDTO;
import com.kamixiya.icm.model.security.role.RoleEditInfoDTO;
import com.kamixiya.icm.model.security.user.UserDTO;
import com.kamixiya.icm.service.common.entity.organization.Employee;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.security.Authority;
import com.kamixiya.icm.service.common.entity.security.Role;
import com.kamixiya.icm.service.common.entity.security.User;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.filter.CurrentUserUtil;
import com.kamixiya.icm.service.common.mapper.organization.OrganizationMapper;
import com.kamixiya.icm.service.common.mapper.security.AuthorityMapper;
import com.kamixiya.icm.service.common.mapper.security.RoleMapper;
import com.kamixiya.icm.service.common.repository.organization.OrganizationRepository;
import com.kamixiya.icm.service.common.repository.security.AuthorityRepository;
import com.kamixiya.icm.service.common.repository.security.RoleRepository;
import com.kamixiya.icm.service.common.repository.security.UserRepository;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Stream;

/**
 * 角色服务实现类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Service("roleService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final AuthorityMapper authorityMapper;
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public RoleServiceImpl(AuthorityRepository authorityRepository, RoleRepository roleRepository, RoleMapper roleMapper, OrganizationRepository organizationRepository,
                           AuthorityMapper authorityMapper, OrganizationMapper organizationMapper, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.organizationRepository = organizationRepository;
        this.authorityMapper = authorityMapper;
        this.organizationMapper = organizationMapper;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO findById(String id) {
        Optional<Role> role = roleRepository.findById(Long.valueOf(id));
        if (role.isPresent()) {
            return roleMapper.toDTO(role.get());
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Set<UserDTO> findUsers(String id) {

        Optional<Role> o = roleRepository.findById(Long.valueOf(id));
        if (o.isPresent()) {
            return roleMapper.toSetUserDto(o.get().getUsers());
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Set<AuthorityDTO> findAuthorities(String id) {
        Optional<Role> o = roleRepository.findById(Long.valueOf(id));
        if (o.isPresent()) {
            return authorityMapper.toSetDTO(o.get().getAuthorities());
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Set<OrganizationDTO> findOrganizations(String id) {
        Optional<Role> o = roleRepository.findById(Long.valueOf(id));
        if (o.isPresent()) {
            return organizationMapper.toSetDTO(o.get().getOrganizations(), new CycleAvoidingMappingContext());
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageDataDTO<RoleDTO> findOnePage(int page, int size, String sort, String name, Boolean isAll) {
        Set<Long> roleIds = new HashSet<>();
        if (!isAll) {
            String userId = CurrentUserUtil.getInstance().getUserId();
            String defaultOrganizationId = CurrentUserUtil.getInstance().getWorkingOrganizationId();
            Optional<User> o = userRepository.findById(Long.parseLong(userId));
            if (o.isPresent()) {
                User user = o.get();
                user.getRoles().forEach(role -> roleIds.add(role.getId()));
                Employee employee = user.getEmployee();
                // 给当前用户设置所拥有的组织
                if (employee != null) {
                    Set<Organization> organizationSetOLd = employee.getOrganizations();
                    // 默认岗位角色
                    if (defaultOrganizationId != null) {
                        Optional<Organization> organization = organizationRepository.findById(Long.valueOf(defaultOrganizationId));
                        if (organization.isPresent()) {
                            organization.get().getRoles().forEach(role -> roleIds.add(role.getId()));
                        } else {
                            throw new NoSuchDataException(userId);
                        }
                    } else {
                        // 岗位权限
                        organizationSetOLd.forEach(organization -> organization.getRoles().forEach(role -> roleIds.add(role.getId())));
                    }
                }
            } else {
                throw new NoSuchDataException(userId);
            }
        }
        return findOnePage(page, size, sort, name, customizeRoleIds(roleIds));
    }

    protected Set<Long> customizeRoleIds(Set<Long> roleIds) {
        return roleIds;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageDataDTO<RoleDTO> findOnePage(int page, int size, String sort, String name, Set<Long> roleIds) {
        Specification<Role> specification = ((Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> conditions = new ArrayList<>();
            if (name != null) {
                conditions.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
            }

            if (!roleIds.isEmpty()) {
                CriteriaBuilder.In<Long> inRole = cb.in(root.get("id").as(Long.class));
                for (Long id : roleIds) {
                    inRole.value(id);
                }
                conditions.add(cb.or(inRole));
            }


            if (!conditions.isEmpty()) {
                Predicate[] array = new Predicate[conditions.size()];
                query.where(conditions.toArray(array));
            }
            return null;
        });
        Page<Role> rolePage = roleRepository.findAll(specification, PageHelper.generatePageRequest(page, size, sort));
        List<RoleDTO> list = new ArrayList<>();
        rolePage.getContent().forEach(role -> {
            RoleDTO roleDTO = roleMapper.toDTO(role);
            Set<OrganizationDTO> organizationDTOS = new LinkedHashSet<>();
            Set<AuthorityBaseDTO> authorityBaseDTOS = new LinkedHashSet<>();
            role.getOrganizations().forEach(organization -> {
                OrganizationDTO organizationDTO = organizationMapper.toDTO(organization, new CycleAvoidingMappingContext());
                organizationDTOS.add(organizationDTO);
            });
            role.getAuthorities().forEach(authority -> {
                AuthorityBaseDTO authorityDTO = authorityMapper.toBaseDTO(authority);
                authorityBaseDTOS.add(authorityDTO);
            });
            roleDTO.setAssignedOrganizations(organizationDTOS);
            roleDTO.setAssignedAuthority(authorityBaseDTOS);
            list.add(roleDTO);
        });
        return PageDataUtil.toPageData(rolePage, list);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO create(RoleEditInfoDTO roleEditInfoDTO) {
        Role role = roleMapper.toEntity(roleEditInfoDTO);
        role = roleRepository.save(role);
        if (roleEditInfoDTO.getIsAll() != null && !roleEditInfoDTO.getIsAll()) {
            String userId = CurrentUserUtil.getInstance().getUserId();
            Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                user.getRoles().add(role);
                userRepository.save(user);

                role.getUsers().add(user);
                roleRepository.save(role);
            } else {
                throw new NoSuchDataException(userId);
            }
        }
        RoleDTO roleDTO = roleMapper.toDTO(role);
        setOrganizationsOrAuthorities(roleDTO, roleEditInfoDTO.getOrganizationIds(), roleEditInfoDTO.getAuthorityIds(), role);
        return roleDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantUsers(String id, String[] data) {
        Optional<Role> o = roleRepository.findById(Long.valueOf(id));
        if (!o.isPresent()) {
            throw new NoSuchDataException(id);
        } else {
            long[] ids = Stream.of(data).mapToLong(Long::valueOf).toArray();
            Set<User> users = new HashSet<>();
            if (ids != null && ids.length > 0) {
                users = userRepository.findUsers(ids);
            }

            // 先解除已分配用户的关联关系
            o.get().getUsers().forEach(u -> {
                u.getRoles().remove(o.get());
                userRepository.save(u);
            });

            // 再分配新的用户
            users.forEach(u -> {
                u.getRoles().add(o.get());
                userRepository.save(u);
            });

            o.get().setUsers(users);
            roleRepository.save(o.get());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO update(String id, RoleEditInfoDTO roleEditInfoDTO) {
        Optional<Role> old = roleRepository.findById(Long.valueOf(id));
        Role role = old.orElseThrow(() -> new NoSuchDataException(id));
        roleMapper.updateEntity(roleEditInfoDTO, role);
        role = roleRepository.save(role);
        RoleDTO roleDTO = roleMapper.toDTO(role);
        setOrganizationsOrAuthorities(roleDTO, roleEditInfoDTO.getOrganizationIds(), roleEditInfoDTO.getAuthorityIds(), role);
        return roleDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Optional<Role> o = roleRepository.findById(Long.valueOf(id));
        if (o.isPresent()) {
            // 解除用户与角色的关联
            o.get().getUsers().forEach(u -> {
                u.getRoles().remove(o.get());
                userRepository.save(u);
            });

            // 解除权限与角色的关联
            o.get().getAuthorities().forEach(u -> {
                u.getRoles().remove(o.get());
                authorityRepository.save(u);
            });

            // 删除角色
            roleRepository.deleteById(Long.valueOf(id));
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean checkNameAvailability(String roleId, String name) {
        List<Role> all = roleRepository.findAll(
                Specifications.<Role>and()
                        .eq(roleId != null, "id", roleId)
                        .eq("name", name)
                        .build());
        if (roleId != null && !all.isEmpty()) {
            return true;
        } else {
            return all.isEmpty();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Set<String> getRoleAuthorities(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent()) {
            throw new NoSuchDataException(id.toString());
        }
        Set<String> authorities = new HashSet<>();
        role.get().getAuthorities().forEach(rr -> authorities.add(rr.getCode()));
        return authorities;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Set<String> getOrganizationAuthorities(Long organizationId) {
        Set<String> workingOrganizations = new HashSet<>();
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        organization.ifPresent(organization1 -> organization1.getRoles().forEach(r -> workingOrganizations.addAll(this.getRoleAuthorities(r.getId()))));
        return workingOrganizations;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantAuthorities(String id, String... data) {
        Optional<Role> roleOld = roleRepository.findById(Long.valueOf(id));
        long[] ids = Stream.of(data).mapToLong(Long::parseLong).toArray();
        Set<Authority> authorities = new HashSet<>();
        if (ids != null && ids.length > 0) {
            authorities = authorityRepository.findAuthorities(ids);
        }
        if (roleOld.isPresent()) {
            // 先解除已分配权限的关联关系
            roleOld.get().getAuthorities().forEach(r -> {
                r.getRoles().remove(roleOld.get());
                authorityRepository.save(r);
            });

            // 再分配权限
            authorities.forEach(r -> {
                r.getRoles().add(roleOld.get());
                authorityRepository.save(r);
            });

            roleOld.get().setAuthorities(authorities);
            roleRepository.save(roleOld.get());
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantOrganizations(String id, String... data) {
        Optional<Role> roleOld = roleRepository.findById(Long.valueOf(id));
        long[] ids = Stream.of(data).mapToLong(Long::parseLong).toArray();
        Set<Organization> organizations = new HashSet<>();
        if (ids != null && ids.length > 0) {
            organizations = organizationRepository.findOrganizations(ids);
        }
        if (roleOld.isPresent()) {
            // 先解除已分配组织的关联关系
            roleOld.get().getOrganizations().forEach(r -> {
                r.getRoles().remove(roleOld.get());
                organizationRepository.save(r);
            });

            // 再分配组织
            organizations.forEach(r -> {
                r.getRoles().add(roleOld.get());
                organizationRepository.save(r);
            });

            roleOld.get().setOrganizations(organizations);
            roleRepository.save(roleOld.get());
        } else {
            throw new NoSuchDataException(id);
        }
    }

    private void setOrganizationsOrAuthorities(RoleDTO
                                                       roleDTO, Set<String> assignedOrganizationIds, Set<String> assignedAuthorityIds, Role role) {
        if (assignedOrganizationIds != null && !assignedOrganizationIds.isEmpty()) {
            Set<OrganizationDTO> organizationDTOS = new LinkedHashSet<>();
            assignedOrganizationIds.forEach(oid -> {
                Optional<Organization> optionalOrganization = organizationRepository.findById(Long.valueOf(oid));
                Organization organization = optionalOrganization.orElseThrow(() -> new NoSuchDataException(oid));
                role.getOrganizations().add(organization);
                organizationDTOS.add(organizationMapper.toDTO(organization, new CycleAvoidingMappingContext()));
            });
            roleDTO.setAssignedOrganizations(organizationDTOS);
        }
        if (assignedAuthorityIds != null && !assignedAuthorityIds.isEmpty()) {
            Set<AuthorityBaseDTO> authorityDTOS = new LinkedHashSet<>();
            assignedAuthorityIds.forEach(aid -> {
                Optional<Authority> optionalAuthority = authorityRepository.findById(Long.valueOf(aid));
                Authority authority = optionalAuthority.orElseThrow(() -> new NoSuchDataException(aid));
                role.getAuthorities().add(authority);
                authorityDTOS.add(authorityMapper.toBaseDTO(authority));
            });
            roleDTO.setAssignedAuthority(authorityDTOS);
        }
    }
}
