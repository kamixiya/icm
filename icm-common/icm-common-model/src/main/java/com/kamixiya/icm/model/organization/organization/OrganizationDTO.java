package com.kamixiya.icm.model.organization.organization;

import com.kamixiya.icm.model.organization.department.DepartmentBaseDTO;
import com.kamixiya.icm.model.organization.position.PositionBaseDTO;
import com.kamixiya.icm.model.organization.unit.UnitBaseDTO;
import com.kamixiya.icm.model.security.role.RoleBaseDTO;
import com.kamixiya.icm.model.security.user.UserBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * OrganizationDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "组织信息")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
@ToString
public class OrganizationDTO extends OrganizationBaseDTO implements Serializable {

    @ApiModelProperty(position = 131, notes = "子资源列表")
    private List<OrganizationDTO> children;

    @ApiModelProperty(position = 132, notes = "父组织ID")
    private OrganizationDTO parent;

    @ApiModelProperty(position = 150, value = "关联的单位，如果类型是单位的话")
    private UnitBaseDTO unit;

    @ApiModelProperty(position = 160, value = "关联的部门，如果类型是部门的话")
    private DepartmentBaseDTO department;

    @ApiModelProperty(position = 170, value = "关联的岗位，如果类型是岗位的话")
    private PositionBaseDTO position;


    @ApiModelProperty(position = 181, value = "拥有该组织的所有角色ID")
    private Set<RoleBaseDTO> assignedRoles;

    @ApiModelProperty(position = 182, value = "数据过滤路径")
    private String filterPath;

    @ApiModelProperty(position = 391, notes = "属于组织员工对应的用户")
    private List<UserBaseDTO> userBaseDTOS;

}