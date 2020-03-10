package com.kamixiya.icm.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Null;
import java.util.Set;

/**
 * 用户信息
 *
 * @author Zhu Jie
 * @date 2020/3/8
 */
@ApiModel(description = "用户信息")
@Getter
@Setter
@ToString(callSuper = true)
public class UserDTO extends UserBaseDTO{

    @ApiModelProperty(position = 310, value = "账号是否被锁住（通常是连续输错密码）")
    @Null
    private Boolean accountLocked;

    @ApiModelProperty(position = 320, value = "权限（所属角色和所属组织的权限）")
    private Set<String> authorities;
}
