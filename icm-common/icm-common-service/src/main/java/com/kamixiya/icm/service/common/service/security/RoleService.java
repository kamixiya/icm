package com.kamixiya.icm.service.common.service.security;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.security.authority.AuthorityDTO;
import com.kamixiya.icm.model.security.role.RoleDTO;
import com.kamixiya.icm.model.security.role.RoleEditInfoDTO;
import com.kamixiya.icm.model.security.user.UserDTO;

import java.util.Set;

/**
 * 角色服务
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
public interface RoleService {

    /**
     * 根据角色id查询角色信息
     *
     * @param id 角色id
     * @return 角色dto信息
     */
    RoleDTO findById(String id);

    /**
     * 取得角色分配的用户
     *
     * @param id 角色id
     * @return 用户dto信息集合
     */
    Set<UserDTO> findUsers(String id);

    /**
     * 取得角色分配的权限
     *
     * @param id 角色id
     * @return 权限dto信息
     */
    Set<AuthorityDTO> findAuthorities(String id);

    /**
     * 取得角色分配的组织
     *
     * @param id 角色id
     * @return 组织dto信息集合
     */
    Set<OrganizationDTO> findOrganizations(String id);

    /**
     * 分页查询角色信息
     *
     * @param page  页号
     * @param size  每页大小
     * @param sort  排序字段, 例如：字段1,asc,字段2,desc
     * @param name  名称, 支持模糊查找
     * @param isAll 是否所有
     * @return 角色dto信息集合
     */
    PageDataDTO<RoleDTO> findOnePage(int page, int size, String sort, String name, Boolean isAll);

    /**
     * 分页查询角色信息
     *
     * @param page    页号
     * @param size    每页大小
     * @param sort    排序字段, 例如：字段1,asc,字段2,desc
     * @param name    名称, 支持模糊查找
     * @param roleIds 只存在的角色集合
     * @return 角色dto信息集合
     */
    PageDataDTO<RoleDTO> findOnePage(int page, int size, String sort, String name, Set<Long> roleIds);

    /**
     * 创建角色信息
     *
     * @param roleEditInfoDTO 角色信息
     * @return 角色dto信息
     */
    RoleDTO create(RoleEditInfoDTO roleEditInfoDTO);

    /**
     * 给角色分配用户
     *
     * @param id   角色id
     * @param data 用户id数组
     */
    void grantUsers(String id, String[] data);

    /**
     * 给角色分配权限
     *
     * @param id   角色id
     * @param data 权限id数组
     */
    void grantAuthorities(String id, String... data);

    /**
     * 给角色分配组织
     *
     * @param id   角色id
     * @param data 组织id数组
     */
    void grantOrganizations(String id, String... data);

    /**
     * 修改角色信息
     *
     * @param id              角色id
     * @param roleEditInfoDTO 角色信息
     * @return 角色dto信息
     */
    RoleDTO update(String id, RoleEditInfoDTO roleEditInfoDTO);

    /**
     * 删除角色信息
     *
     * @param id 角色id
     */
    void delete(String id);

    /**
     * 查询角色名称是否可用
     *
     * @param roleId 角色id
     * @param name   角色名称
     * @return true 可用,false 不可用
     */
    Boolean checkNameAvailability(String roleId, String name);

    /**
     * 获取指定角色的权限清单
     *
     * @param id 角色ID
     * @return 权限清单
     */
    Set<String> getRoleAuthorities(Long id);

    /**
     * 根据组织id获取组织管理角色的权限
     *
     * @param organizationId 组织id
     * @return 组织所包含的权限
     */
    Set<String> getOrganizationAuthorities(Long organizationId);
}
