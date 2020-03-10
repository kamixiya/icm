package com.kamixiya.icm.service.common.security;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.role.RoleDTO;
import com.kamixiya.icm.model.user.PasswordInfoDTO;
import com.kamixiya.icm.model.user.UserDTO;
import com.kamixiya.icm.model.user.UserEditInfoDTO;

import java.util.Collection;
import java.util.List;

/**
 * 用户服务
 *
 * @author Zhu Jie
 * @date 2020/3/10
 */
public interface UserService {

    /**
     * 通过ID查找用户
     *
     * @param id 用户ID
     * @return 用户
     */
    UserDTO findById(String id);

    /**
     * 分页查询用户
     *
     * @param page 页号
     * @param size 每页纪录条数
     * @param sort 排序字段, 例如：字段1,asc,字段2,desc
     * @param name 用户名,支持模糊查询
     * @return 用户dto信息集合
     */
    PageDataDTO<UserDTO> findOnePage(int page, int size, String sort, String name);

    /**
     * 查询所有用户
     *
     * @param name 用户名,支持模糊查询
     * @return 用户dto信息集合
     */
    List<UserDTO> findAll(String name);

    /**
     * 查询用户登录账号是否可用
     *
     * @param userId  用户id
     * @param account 用户账号
     * @return true 可用,false 不可用
     */
    SimpleDataDTO<Boolean> checkAccountAvailability(String userId, String account);

    /**
     * 修改用户密码（只用于修改当前登录用户的密码）
     *
     * @param userId          用户id
     * @param passwordInfoDTO 修改密码dto
     *
     */
    void changePassword(String userId, PasswordInfoDTO passwordInfoDTO);

    /**
     * 创建用户信息
     *
     * @param userEditInfoDTO 用户信息
     * @return 用户dto信息
     */
    UserDTO create(UserEditInfoDTO userEditInfoDTO);

    /**
     * 修改用户信息
     *
     * @param id              用户id
     * @param userEditInfoDTO 用户信息
     * @return 用户dto信息
     */
    UserDTO update(String id, UserEditInfoDTO userEditInfoDTO);

    /**
     * 删除用户信息
     *
     * @param id 用户id
     */
    void delete(String id);

    /**
     * 根据用户ID查找用户所分配的角色
     *
     * @param id 用户id
     * @return 角色dto信息集合
     */
    List<RoleDTO> getRoles(String id);

    /**
     * 分配角色（重新设置用户的角色）
     *
     * @param id   用户id
     * @param data 角色id数组
     */
    void grantRoles(String id, String[] data);


    /**
     * 切换组织（应用于用户所属的岗位可访问的组织架构数据）
     *
     * @param organizationId 组织id
     * @return 权限值集合
     */
    Collection<String> setWorkingOrganization(String organizationId);

    /**
     * 通过账号来查找用户ID
     *
     * @param account 用户登录账号
     * @return 用户ID 没有则为null
     */
    String findByAccount(String account);

    /**
     * 非当日登录更新失败次数
     *
     * @param id 用户ID
     */
    void updateLoginCountOnAnotherDay(String id);

    /**
     * 登录成功重置失败次数
     *
     * @param id 用户ID
     */
    void resetLoginCountAfterSuccessfulLogin(String id);

    /**
     * 根据id修改用户的失败次数
     * 5次锁定账户
     *
     * @param userId 用户id
     */
    void loginFailed(String userId);
}
