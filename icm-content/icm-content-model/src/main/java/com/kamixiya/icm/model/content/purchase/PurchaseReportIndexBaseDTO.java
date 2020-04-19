package com.kamixiya.icm.model.content.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * PurchaseReportIndexBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "采购申请——指标基本信息")
@Getter
@Setter
@ToString
class PurchaseReportIndexBaseDTO {
    @ApiModelProperty(position = 120, value = "指标余额", required = true)
    @NotNull
    private Double availableAmount;

    @ApiModelProperty(position = 130, value = "数量", required = true)
    @NotNull
    private Double quantity;

    @ApiModelProperty(position = 110, value = "参考单价", required = true)
    @NotNull
    private Double price;

    @ApiModelProperty(position = 140, value = "采购金额")
    private Double amount;

    @ApiModelProperty(position = 150, value = "本年支出金额")
    private Double currentYearAmount;

    @ApiModelProperty(position = 160, value = "占用金额")
    private Double occupationAmount;

    @ApiModelProperty(position = 170, value = "占用金额")
    private Double passageAmount;

}