package com.kamixiya.icm.model.user;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
