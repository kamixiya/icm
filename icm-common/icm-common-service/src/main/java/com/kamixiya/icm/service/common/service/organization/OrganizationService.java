package com.kamixiya.icm.service.common.service.organization;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.common.SimpleTreeDataDTO;
import com.kamixiya.icm.model.organization.department.DepartmentBaseDTO;
import com.kamixiya.icm.model.organization.employee.EmployeeDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.model.organization.position.PositionBaseDTO;
import com.kamixiya.icm.model.organization.unit.UnitBaseDTO;
import com.kamixiya.icm.model.security.role.RoleDTO;
import com.kamixiya.icm.service.common.entity.organization.Organization;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * OrganizationService
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
public interface OrganizationService {
    /**
     * 通过ID查找Organization
     *
     * @param id ID
     * @return 组织架构信息
     */
    Organization findById(Long id);

    /**
     * Organization 查询组织结构树
     *
     * @param employIds      员工或者用户的id数组
     * @param selectedOrgId  组织机构id
     * @param type           指定返回的人员数据类型，默认为用户,employye 为员工
     * @param organzitionIds 组织机构id数组
     * @return 简单树
     */
    List<SimpleTreeDataDTO> findTree(String[] employIds, String selectedOrgId, String type, String[] organzitionIds);

    /**
     * 更改父组织
     *
     * @param childId  子组织ID
     * @param parentId 父组织ID
     * @param ids      有序的组织ID集合
     * @return 组织dto信息集合
     */
    List<OrganizationDTO> changeParent(String childId, String parentId, List<String> ids);

    /**
     * 分页查询组织下的员工,若该组织有子组织那么子组织员工将一并查出
     *
     * @param page                     页号
     * @param size                     每页纪录条数
     * @param sort                     排序字段, 例如：字段1,asc,字段2,desc
     * @param organizationQueryOptions 查询条件集合
     * @return 员工dto信息集合
     */
    PageDataDTO<EmployeeDTO> getEmployees(int page, int size, String sort, OrganizationQueryOptions organizationQueryOptions);

    /**
     * 根据组织Id查询员工
     *
     * @param organizationId 组织ID
     * @param deepLoad       是否查询子组织下的员工
     * @return List<EmployeeDTO>
     */
    List<EmployeeDTO> getEmployeeList(String organizationId, Boolean deepLoad);

    /**
     * 根据组织ID查找组织所分配的角色
     *
     * @param id 组织id
     * @return 角色dto信息集合
     */
    List<RoleDTO> getRoles(String id);

    /**
     * 给组织重新分配角色
     *
     * @param id   组织id
     * @param data 角色id集合
     */
    void grantRoles(String id, String... data);

    /**
     * 根据父ID查询对应的类型子组织，无父ID则查询所有首层对应类型的组织
     *
     * @param parentId         父OrganizationID
     * @param deepLoad         是否查询子单位
     * @param organizationType 组织类型
     * @param exclusive        是否排除不同组织类型
     * @param self             是否返回父组织，如果需要父组织，则返回的列表中只有父组织一条数据
     * @param interior         是否内部
     * @param name             组织名称
     * @return 资源列表
     */
    List<OrganizationDTO> getChildren(String parentId, Boolean deepLoad, Boolean exclusive, OrganizationType organizationType, Boolean self, Boolean interior, String name);

    /**
     * 通过ID查找Organization
     *
     * @param id ID
     * @return 组织架构信息DTO
     */
    OrganizationDTO findByOrgId(String id);

    /**
     * 切换组织
     *
     * @param organizationId 组织id
     * @param id             用户ID
     * @return 组织所包含的权限
     */
    Set<String> getOrganizationAuthorities(String id, String organizationId);

    /**
     * 获取所属的第一个单位
     *
     * @param organizationId 组织id
     * @return 单位dto
     */
    UnitBaseDTO getUnitOrgId(String organizationId);

    /**
     * 获取所属的第一个部门
     *
     * @param organizationId 组织id
     * @return 部门dto
     */
    DepartmentBaseDTO getDepartmentByOrgId(String organizationId);

    /**
     * 获取所属的岗位
     *
     * @param organizationId 组织id
     * @return 岗位dto
     */
    PositionBaseDTO getPositionOrgId(String organizationId);

    /**
     * 根据当前的组织id获取最近的岗位，部门，单位的FilterPath
     *
     * @param organizationId 组织id
     * @return 最近的岗位，部门，单位
     */
    Map<String, String> getUnitAndDepartmentAndPositionFilterPathById(String organizationId);

    /**
     * 生成一个Organization
     *
     * @param name      名称
     * @param shortName 简称
     * @param available 是否可用
     * @param interior  是否内部
     * @param showOrder 显示顺序
     * @param type      类型
     * @return 创建的Organization
     */
    Organization from(String name, String shortName, Boolean available, Boolean interior, Integer showOrder, OrganizationType type);

    /**
     * 更新路径
     *
     * @param organization 组织架构
     * @return 保存后的组织架构
     */
    Organization updateTreePathAndLevel(Organization organization);

    /**
     * 获取当前组织的FilterPath集合（不包含子单位）
     *
     * @param organization 组织
     * @return 组织的FilterPath集合
     */
    Set<String> getCurrentOrgFilterPath(Organization organization);

    /**
     * 根据organizationId查询其下面对应organizationType的组织
     *
     * @param organizationId   组织id
     * @param organizationType 组织类型
     * @return 组织集合
     */
    Set<OrganizationDTO> findByOrganizationIdAndOrganizationType(String organizationId, OrganizationType organizationType);

    /**
     * 根据用户id获取其默认的组织在根据默认的组织和组织类型取最近的组织类型所对应的filterPath
     *
     * @param userId           用户id
     * @param organizationType 组织类型
     * @return 过滤路径
     */
    String getFilterPathByUserId(String userId, OrganizationType organizationType);


    /**
     * 根据组织id和组织类型获取最近的组织类型所对应的组织
     *
     * @param organizationId   组织id
     * @param organizationType 组织类型
     * @return 组织dto
     */
    OrganizationDTO getRecentlyOrganization(String organizationId, OrganizationType organizationType);

    /**
     * 根据用户id和组织id查出所有的岗位
     *
     * @param userId         用户id
     * @param organizationId 组织id
     * @return 岗位dto
     */
    List<PositionBaseDTO> getPositionBaseDtoByUserIdAndOrganizationId(String userId, String organizationId);

    /**
     * 根据用户id和组织类型获取工作组织
     *
     * @param userId           用户id
     * @param organizationType 组织类型
     * @return 获取workingOrganization
     */
    String getWorkingOrganizationByUserIdAndOrganizationType(String userId, OrganizationType organizationType);

    /**
     * 查询所有组织集合
     *
     * @param name             组织名称, 支持模糊查找
     * @param organizationType 组织类型
     * @return 组织集合dto
     */
    List<OrganizationDTO> findAll(String name, OrganizationType organizationType);

    /**
     * 分页查询组织集合
     *
     * @param page             页号
     * @param size             每页大小
     * @param sort             排序字段, 例如：字段1,asc,字段2,desc
     * @param name             组织名称, 支持模糊查找
     * @param organizationType 组织类型
     * @return 组织集合dto
     */
    PageDataDTO<OrganizationDTO> findOnePage(int page, int size, String sort, String name, OrganizationType organizationType);
}
