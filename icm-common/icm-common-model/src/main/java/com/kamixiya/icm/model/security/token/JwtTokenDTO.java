package com.kamixiya.icm.model.security.token;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 用户成功登录后，返回的数据类，包含用户信息，token等
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "登录成功后，返回的数据类")
@Getter
@Setter
@ToString
public class JwtTokenDTO {

    @ApiModelProperty(position = 1, value = "用户ID", required = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String userId;

    @ApiModelProperty(position = 100, value = "用户姓名", required = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String userName;

    @ApiModelProperty(position = 110, value = "令牌生成时间", required = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY, example = "2018-12-31 23:59:59")
    private Date issued;

    @ApiModelProperty(position = 120, value = "令牌失效时间", required = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY, example = "2018-12-31 23:59:59")
    private Date expires;

    @ApiModelProperty(position = 130, value = "令牌", required = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String token;

    @ApiModelProperty(position = 140, value = "当前工作组织的filterPath", required = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String workingOrganization;

}