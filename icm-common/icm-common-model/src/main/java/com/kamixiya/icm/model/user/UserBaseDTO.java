package com.kamixiya.icm.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户基础信息
 *
 * @author Zhu Jie
 * @date 2020/3/8
 */
@ApiModel(description = "用户基础信息")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class UserBaseDTO implements Serializable {

    private static final String PASS_PLACE_HOLDER = "$pWd$(C%#*(78))!<>";

    @ApiModelProperty(position = 1, notes = "对象ID，新增时应当为null, 系统会自动生成", required = true)
    private String id;

    @ApiModelProperty(position = 110, value = "登录账号, 要求在创建时验证唯一性", required = true)
    @NotNull
    @Size(min = 1, max = 20)
    private String account;

    @ApiModelProperty(position = 120, value = "姓名", required = true)
    @NotNull
    @Size(min = 1, max = 20)
    private String name;

    @ApiModelProperty(position = 130, value = "密码", required = true)
    @Size(min = 8, max = 88)
    @JsonIgnore
    private String password;

    @ApiModelProperty(position = 140, value = "密码失效时间, null表示永久有效", example = "2018-12-31 23:59:59")
    private Date passwordExpireDate;

    @ApiModelProperty(position = 150, value = "账号是否激活", required = true)
    @NotNull
    private Boolean enabled;

    @ApiModelProperty(position = 151, value = "账号失效时间，null表示永久有效", example = "2018-12-31 23:59:59")
    private Date accountExpireDate;

    @ApiModelProperty(position = 160, value = "是否本地登录账号，非本地账号需要连接OA或其他系统验证登录", required = true)
    @NotNull
    private Boolean localAccount;

    @ApiModelProperty(position = 170, value = "备注")
    @Size(max = 500)
    private String remark;

    @JsonProperty
    public String password() {
        return PASS_PLACE_HOLDER;
    }
}
