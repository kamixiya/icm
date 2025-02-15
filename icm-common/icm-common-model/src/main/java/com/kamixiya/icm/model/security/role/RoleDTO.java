package com.kamixiya.icm.model.security.role;

import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.security.authority.AuthorityBaseDTO;
import com.kamixiya.icm.model.security.user.UserBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * 角色详细信息
 *
 * @author Zhu Jie
 * @date 2020/3/11
 */
@ApiModel(description = "角色详细信息")
@Getter
@Setter
@ToString(callSuper = true)
public class RoleDTO extends RoleBaseDTO{

    @ApiModelProperty(position = 310, value = "创建数据的用户ID，系统根据token自动填写", required = true)
    private UserBaseDTO createUser;

    @ApiModelProperty(position = 360, value = "拥有该角色的所有所有组织ID")
    private Set<OrganizationDTO> assignedOrganizations;

    @ApiModelProperty(position = 370, value = "拥有该角色的所有所有权限ID")
    private Set<AuthorityBaseDTO> assignedAuthority;

}
