package com.kamixiya.icm.model.content.revenue.opening;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * BankAccountOpeningBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户开户基本信息")
@Getter
@Setter
@ToString
class BankAccountOpeningBaseDTO {

    @ApiModelProperty(position = 160, value = "申请单号", required = true)
    private String code;

    @ApiModelProperty(position = 170, value = "申请时间", required = true)
    private Date applyDate;

    @ApiModelProperty(position = 180, value = "账户性质", required = true)
    private String accountProperty;

    @ApiModelProperty(position = 185, value = "单位性质", required = true)
    private String unitProperty;

    @ApiModelProperty(position = 190, value = "开户银行", required = true)
    private String nameOfBank;

    @ApiModelProperty(position = 200, value = "开户银行全称", required = true)
    private String fullNameOfBank;

    @ApiModelProperty(position = 210, value = "政府依据")
    private String governmentBasic;

    @ApiModelProperty(position = 220, value = "选择原因")
    private String reason;
}