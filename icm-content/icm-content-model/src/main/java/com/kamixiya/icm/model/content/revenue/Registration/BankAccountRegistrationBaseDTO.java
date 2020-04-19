package com.kamixiya.icm.model.content.revenue.Registration;

import com.kamixiya.icm.persistence.content.entity.revenue.AccountState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * icm-server.com.matech.icm.model.revenue.nontax
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户登记基础信息")
@Getter
@Setter
@ToString
class BankAccountRegistrationBaseDTO {
    @ApiModelProperty(position = 150, value = "法定代表人", required = true)
    private String legalRepresentative;

    @ApiModelProperty(position = 160, value = "账户名称", required = true)
    private String accountName;

    @ApiModelProperty(position = 170, value = "开户时间", required = true)
    private Date openTime;

    @ApiModelProperty(position = 175, value = "开户银行", required = true)
    private String nameOfBank;

    @ApiModelProperty(position = 180, value = "账户有效期（年）", required = true)
    private Integer validityPeriod;

    @ApiModelProperty(position = 218, value = "开户银行全称", required = true)
    private String fullNameOfBank;

    @ApiModelProperty(position = 190, value = "账号", required = true)
    private String account;

    @ApiModelProperty(position = 200, value = "账户状态", required = true)
    private AccountState accountState;

    @ApiModelProperty(position = 210, value = "账户性质")
    private String accountProperty;

    @ApiModelProperty(position = 215, value = "单位性质", required = true)
    private String unitProperty;

    @ApiModelProperty(position = 220, value = "账户用途")
    private String accountUse;

    @ApiModelProperty(position = 230, value = "账户币种")
    private String accountCurrency;

    @ApiModelProperty(position = 240, value = "销户原因")
    private String reasonOfCancellation;

    @ApiModelProperty(position = 250, value = "开户许可证号")
    private String accountLicense;

    @ApiModelProperty(position = 260, value = "财政批复书批号")
    private String relyNumber;

    @ApiModelProperty(position = 270, value = "联系人")
    private String accountLink;

    @ApiModelProperty(position = 280, value = "联系电话")
    private String telephone;

    @ApiModelProperty(position = 290, value = "备注")
    private String remark;
}
