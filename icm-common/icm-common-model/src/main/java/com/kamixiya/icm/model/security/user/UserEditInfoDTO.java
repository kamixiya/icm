package com.kamixiya.icm.model.security.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * 新增或者修改用户信息
 *
 * @author Zhu Jie
 * @date 2020/3/11
 */
@ApiModel(description = "新增或者修改用户信息")
@Getter
@Setter
@ToString(callSuper = true)
public class UserEditInfoDTO extends UserBaseDTO{

    @ApiModelProperty(position = 200, value = "该用户拥有的所有角色ID")
    private Set<String> roleIds;

}
