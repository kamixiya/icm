package com.kamixiya.icm.service.common.service.security;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.security.authority.AuthorityDTO;
import com.kamixiya.icm.model.security.authority.AuthorityEditInfoDTO;

import java.util.List;
import java.util.Set;

/**
 * 权限服务接口
 *
 * @author Zhu Jie
 * @date 2020/4/6
 */
public interface AuthorityService {
    /**
     * 根据权限id查询权限信息
     *
     * @param id 权限id
     * @return 权限dto
     */
    AuthorityDTO findById(String id);

    /**
     * 分页查询权限信息
     *
     * @param page  页号
     * @param size  每页纪录条数
     * @param sort  排序字段, 例如：字段1,asc,字段2,desc
     * @param type  权限类型
     * @param name  名称
     * @param code  编码
     * @param isAll true  是所有，false是当前用户
     * @return 权限dto集合
     */
    PageDataDTO<AuthorityDTO> findOnePage(Integer page, Integer size, String sort, String type, String name, String code, Boolean isAll);

    /**
     * 根据所有查询权限信息
     *
     * @return 权限dto集合
     */
    List<AuthorityDTO> findAll();

    /**
     * 检查编号是否可用
     *
     * @param code 权限编号
     * @param id   权限ID,新建权限时不需要
     * @return true 可用,false 不能用
     */
    Boolean checkAvailability(String code, String id);

    /**
     * 创建权限信息
     *
     * @param authorityEditInfoDTO 权限信息
     * @return 权限dto信息
     */
    AuthorityDTO create(AuthorityEditInfoDTO authorityEditInfoDTO);

    /**
     * 修改权限信息
     *
     * @param id                   权限id
     * @param authorityEditInfoDTO 权限信息
     * @return 权限dto信息
     */
    AuthorityDTO update(String id, AuthorityEditInfoDTO authorityEditInfoDTO);

    /**
     * 删除权限信息
     *
     * @param id 权限信息id
     */
    void delete(String id);

    /**
     * 给权限分配角色
     *
     * @param id    权限id
     * @param roles 角色id数组
     */
    void grantRoles(String id, String... roles);

    /**
     * 类型分组查询
     *
     * @param type 类型
     * @return 分组后的类型集合
     */
    Set<String> getAuthorityTypes(String type);
}
