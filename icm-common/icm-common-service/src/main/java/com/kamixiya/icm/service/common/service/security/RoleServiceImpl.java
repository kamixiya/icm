package com.kamixiya.icm.service.common.service.security;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.security.authority.AuthorityDTO;
import com.kamixiya.icm.model.security.role.RoleDTO;
import com.kamixiya.icm.model.security.role.RoleEditInfoDTO;
import com.kamixiya.icm.model.security.user.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 角色服务实现类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Service("roleService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService{
    @Override
    public RoleDTO findById(String id) {
        return null;
    }

    @Override
    public Set<UserDTO> findUsers(String id) {
        return null;
    }

    @Override
    public Set<AuthorityDTO> findAuthorities(String id) {
        return null;
    }

    @Override
    public Set<OrganizationDTO> findOrganizations(String id) {
        return null;
    }

    @Override
    public PageDataDTO<RoleDTO> findOnePage(int page, int size, String sort, String name, Boolean isAll) {
        return null;
    }

    @Override
    public PageDataDTO<RoleDTO> findOnePage(int page, int size, String sort, String name, Set<Long> roleIds) {
        return null;
    }

    @Override
    public RoleDTO create(RoleEditInfoDTO roleEditInfoDTO) {
        return null;
    }

    @Override
    public void grantUsers(String id, String[] data) {

    }

    @Override
    public void grantAuthorities(String id, String... data) {

    }

    @Override
    public void grantOrganizations(String id, String... data) {

    }

    @Override
    public RoleDTO update(String id, RoleEditInfoDTO roleEditInfoDTO) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Boolean checkNameAvailability(String roleId, String name) {
        return null;
    }

    @Override
    public Set<String> getRoleAuthorities(Long id) {
        return null;
    }

    @Override
    public Set<String> getOrganizationAuthorities(Long organizationId) {
        return null;
    }
}
