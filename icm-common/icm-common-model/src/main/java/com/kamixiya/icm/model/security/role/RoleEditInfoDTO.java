package com.kamixiya.icm.model.security.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * RoleEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Getter
@Setter
@ToString(callSuper = true)
public class RoleEditInfoDTO extends RoleBaseDTO {

    @ApiModelProperty(position = 200, value = "拥有该角色的所有组织ID")
    private Set<String> organizationIds;

    @ApiModelProperty(position = 210, value = "拥有该角色的所有权限ID")
    private Set<String> authorityIds;

    @ApiModelProperty(position = 211, value = "true就是所有,false是当前用户")
    private Boolean isAll;
}

