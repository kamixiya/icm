package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——指标基本信息")
@Getter
@Setter
@ToString
class PaymentReportIndexBaseDTO {
    @ApiModelProperty(position = 120, value = "指标余额", required = true)
    @NotNull
    private Double availableAmount;

    @ApiModelProperty(position = 130, value = "申请金额（元）", required = true)
    @NotNull
    private Double amount;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
