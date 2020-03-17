package com.kamixiya.icm.model.security.token;

import com.kamixiya.icm.model.organization.organization.OrganizationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 登录信息，用户登录时，需要填定登录账号，密码及身份，密码在传输时会加密。
 * 用户输入账号后，前端需要调用/api/users/的相关接口取得用户所有的工作岗位，并选择一个岗位作为进入系统后的身份
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "登录信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInfoDTO {

    @ApiModelProperty(position = 1, value = "账号", required = true)
    @NotNull
    private String account;

    @ApiModelProperty(position = 2, value = "密码", required = true)
    @NotNull
    private String password;

    @ApiModelProperty(position = 3, value = "组织机构的类型(用来设置后端保存数据表的时候的filterPath过滤路径)", example = "POSITION")
    private OrganizationType organizationType;

}
