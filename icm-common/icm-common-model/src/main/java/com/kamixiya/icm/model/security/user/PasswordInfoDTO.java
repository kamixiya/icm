package com.kamixiya.icm.model.security.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 修改密码所使用的数据结构
 *
 * @author Zhu Jie
 * @date 2020/3/11
 */
@ApiModel(description = "修改密码的数据结构")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordInfoDTO {

    @ApiModelProperty(position = 1, value = "原密码", required = true)
    @NotNull
    @Size(min = 8, max = 88)
    private String oldPassword;

    @ApiModelProperty(position = 2, value = "新密码", required = true)
    @NotNull
    @Size(min = 8, max = 88)
    private String newPassword;
}
