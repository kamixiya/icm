package com.kamixiya.icm.model.security.authority;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * AuthorityEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "创建权限信息")
@Getter
@Setter
@ToString(callSuper = true)
public class AuthorityEditInfoDTO extends AuthorityBaseDTO {

    @ApiModelProperty(position = 210, value = "拥有该权限的所有角色ID")
    private String[] roleIds;

}
