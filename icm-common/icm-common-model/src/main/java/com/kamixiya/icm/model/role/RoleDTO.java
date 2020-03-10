package com.kamixiya.icm.model.role;

import com.kamixiya.icm.model.user.UserBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
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

}
