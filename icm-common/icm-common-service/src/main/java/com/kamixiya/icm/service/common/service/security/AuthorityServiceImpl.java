package com.kamixiya.icm.service.common.service.security;

import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.security.authority.AuthorityDTO;
import com.kamixiya.icm.model.security.authority.AuthorityEditInfoDTO;
import com.kamixiya.icm.service.common.entity.organization.Employee;
import com.kamixiya.icm.service.common.entity.security.Authority;
import com.kamixiya.icm.service.common.entity.security.Role;
import com.kamixiya.icm.service.common.entity.security.User;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.filter.CurrentUserUtil;
import com.kamixiya.icm.service.common.mapper.security.AuthorityMapper;
import com.kamixiya.icm.service.common.mapper.security.RoleMapper;
import com.kamixiya.icm.service.common.repository.security.AuthorityRepository;
import com.kamixiya.icm.service.common.repository.security.RoleRepository;
import com.kamixiya.icm.service.common.repository.security.UserRepository;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

/**
 * AuthorityServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/4/6
 */
@Slf4j
@Service("authorityService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserRepository userRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper, RoleRepository roleRepository, RoleMapper roleMapper, UserRepository userRepository) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthorityDTO findById(String id) {
        Optional<Authority> u = authorityRepository.findById(Long.valueOf(id));
        if (u.isPresent()) {
            Set<Role> roles = u.get().getRoles();
            AuthorityDTO authorityDTO = authorityMapper.toDTO(u.get());
            authorityDTO.setAssignedRoles(roleMapper.toSetBaseDTO(roles));
            return authorityDTO;
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageDataDTO<AuthorityDTO> findOnePage(Integer page, Integer size, String sort, String type, String name, String code, Boolean isAll) {
        Set<Long> ids = new HashSet<>();
        if (!isAll) {
            String userId = CurrentUserUtil.getInstance().getUserId();
            Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.getRoles().forEach(role -> {
                    role.getAuthorities().forEach(
                            authority -> ids.add(authority.getId()));
                });

                Employee employee = user.getEmployee();
                if (employee != null) {
                    employee.getOrganizations().forEach(organization ->
                            organization.getRoles().forEach(role ->
                                    role.getAuthorities().forEach(authority -> ids.add(authority.getId()))));
                }

            } else {
                throw new NoSuchDataException(userId);
            }
        }
        Page<Authority> authorityPage = authorityRepository.findAll(
                Specifications.<Authority>and()
                        .like(type != null && !"".equals(type) && !"null".equals(type), "type", "%" + type + "%")
                        .like(name != null && !"".equals(name) && !"null".equals(name), "name", "%" + name + "%")
                        .like(code != null && !"".equals(code) && !"null".equals(code), "code", "%" + code + "%")
                        .in(!ids.isEmpty(), "id", ids.toArray())
                        .build(), PageHelper.generatePageRequest(page, size, sort));
        List<Authority> authorities = authorityPage.getContent();
        List<AuthorityDTO> list = new ArrayList<>();
        authorities.forEach(authority -> {
            AuthorityDTO authorityDTO = authorityMapper.toDTO(authority);
            Set<Role> roles = authority.getRoles();
            authorityDTO.setAssignedRoles(roleMapper.toSetBaseDTO(roles));
            list.add(authorityDTO);
        });

        return PageDataUtil.toPageData(authorityPage, list);
    }

    @Override
    public List<AuthorityDTO> findAll() {
        return authorityMapper.toListDTO(authorityRepository.findAll());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean checkAvailability(String code, String id) {

        Long authorityID = authorityRepository.findAuthoritiesByCode(code);
        if (authorityID == null) {
            return true;
        } else {
            return id != null && id.equalsIgnoreCase(String.valueOf(authorityID));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthorityDTO create(AuthorityEditInfoDTO authorityEditInfoDTO) {
        Authority authority = authorityMapper.toEntity(authorityEditInfoDTO);
        authority = authorityRepository.saveAndFlush(authority);
        String[] roleIds = null;
        if (authorityEditInfoDTO.getRoleIds() != null) {
            roleIds = authorityEditInfoDTO.getRoleIds();
        }
        if (roleIds != null && roleIds.length > 0) {
            long[] ids = Stream.of(roleIds).mapToLong(Long::parseLong).toArray();
            Set<Role> roleSet = new HashSet<>();
            if (ids != null && ids.length > 0) {
                roleSet = roleRepository.findRoles(ids);
            }
            // 分配角色
            Authority finalAuthority = authority;
            roleSet.forEach(r -> {
                r.getAuthorities().add(finalAuthority);
                roleRepository.save(r);
            });
            authority.setRoles(roleSet);
            authority = authorityRepository.saveAndFlush(authority);
        }

        AuthorityDTO authorityDTO = authorityMapper.toDTO(authority);
        authorityDTO.setAssignedRoles(roleMapper.toSetBaseDTO(authority.getRoles()));
        return authorityDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthorityDTO update(String id, AuthorityEditInfoDTO authorityEditInfoDTO) {
        Optional<Authority> o = authorityRepository.findById(Long.valueOf(id));
        Authority authority;
        if (o.isPresent()) {
            authority = o.get();
            authorityMapper.updateEntity(authorityEditInfoDTO, authority);
            // 先解除已分配角色的关联关系
            Authority finalAuthority1 = authority;
            authority.getRoles().forEach(r -> {
                r.getAuthorities().remove(finalAuthority1);
                roleRepository.save(r);
            });
            String[] roleIds = null;
            if (authorityEditInfoDTO.getRoleIds() != null) {
                roleIds = authorityEditInfoDTO.getRoleIds();
            }
            if (roleIds != null && roleIds.length > 0) {
                long[] ids = Stream.of(roleIds).mapToLong(Long::parseLong).toArray();
                Set<Role> roleSet = new HashSet<>();
                if (ids != null && ids.length > 0) {
                    roleSet = roleRepository.findRoles(ids);
                }
                Authority finalAuthority = authority;
                roleSet.forEach(r -> {
                    r.getAuthorities().add(finalAuthority);
                    roleRepository.save(r);
                });
                authority.setRoles(roleSet);

            }
            authority = authorityRepository.saveAndFlush(authority);
            AuthorityDTO authorityDTO = authorityMapper.toDTO(authority);
            authorityDTO.setAssignedRoles(roleMapper.toSetBaseDTO(authority.getRoles()));
            return authorityDTO;
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Optional<Authority> old = authorityRepository.findById(Long.valueOf(id));
        if (old.isPresent()) {
            // 先解除已分配角色的关联关系
            old.get().getRoles().forEach(r -> {
                r.getAuthorities().remove(old.get());
                roleRepository.save(r);
            });

            authorityRepository.delete(old.get());
        } else {
            throw new NoSuchDataException(id);
        }
    }

    protected void customizeVerify() {
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRoles(String id, String... roles) {
        customizeVerify();
        Optional<Authority> old = authorityRepository.findById(Long.valueOf(id));
        long[] ids = Stream.of(roles).mapToLong(Long::parseLong).toArray();
        Set<Role> roleSet = new HashSet<>();
        if (ids != null && ids.length > 0) {
            roleSet = roleRepository.findRoles(ids);
        }
        if (!old.isPresent()) {
            throw new NoSuchDataException(id);
        } else {

            // 先解除已分配角色的关联关系
            old.get().getRoles().forEach(r -> {
                r.getAuthorities().remove(old.get());
                roleRepository.save(r);
            });

            // 再分配角色
            roleSet.forEach(r -> {
                r.getAuthorities().add(old.get());
                roleRepository.save(r);
            });

            old.get().setRoles(roleSet);
            authorityRepository.save(old.get());
        }


    }

    @Override
    public Set<String> getAuthorityTypes(String type) {
        if (type != null) {
            return authorityRepository.getAuthorityTypes(type);
        } else {
            return authorityRepository.getAuthorityTypes();
        }
    }
}
