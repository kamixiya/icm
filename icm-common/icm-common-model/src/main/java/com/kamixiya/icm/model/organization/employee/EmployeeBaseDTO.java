package com.kamixiya.icm.model.organization.employee;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * EmployeeBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class EmployeeBaseDTO implements Serializable {

    @ApiModelProperty(position = 1, notes = "ID")
    private String id;

    @ApiModelProperty(position = 2, value = "员工编号")
    @Size(max = 50)
    private String code;

    @ApiModelProperty(position = 3, value = "邮箱")
    @Size(max = 50)
    @Pattern(regexp = "^\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}$", message = "邮箱验证错误!")
    private String email;

    @ApiModelProperty(position = 4, value = "联系方式")
    @Size(max = 50)
    private String contactInformation;

    @ApiModelProperty(position = 110, value = "员工姓名", required = true)
    @NotNull
    @Size(max = 50)
    private String name;

    @ApiModelProperty(position = 120, value = "出生日期")
    private Date birthDate;

    @ApiModelProperty(position = 130, value = "性别")
    private GenderType gender;

    @ApiModelProperty(position = 160, value = "入职日期")
    private Date joinTime;

    @ApiModelProperty(position = 170, value = "员工在OA系统中的ID")
    @Size(max = 40)
    private String oaId;

    @ApiModelProperty(position = 180, value = "员工在OA系统中的登录账号")
    @Size(max = 30)
    private String oaAccount;

    @ApiModelProperty(position = 190, value = "员工在OA系统中的密码")
    @Size(max = 60)
    private String oaPassword;

    @ApiModelProperty(position = 200, value = "员工的可用状态", required = true)
    @NotNull
    private Boolean available;

    @ApiModelProperty(position = 210, value = "显示顺序", required = true)
    @NotNull
    private Integer showOrder;

    @ApiModelProperty(position = 220, value = "默认组织(岗位)id", required = true)
    private String defaultOrganizationId;

}
