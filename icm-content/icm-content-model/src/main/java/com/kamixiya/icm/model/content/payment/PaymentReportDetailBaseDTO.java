package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——支出详情基本信息")
@Getter
@Setter
@ToString
class PaymentReportDetailBaseDTO {
    @ApiModelProperty(position = 110, value = "支出内容")
    @Size(max = 200)
    private String content;

    @ApiModelProperty(position = 130, value = "参考标准（元/人天）")
    @NotNull
    private Double referenceStandard;

    @ApiModelProperty(position = 135, value = "申请金额（人天）,外币")
    private Double foreignAmount;

    @ApiModelProperty(position = 140, value = "申请金额（元/人天）")
    private Double amount;

    @ApiModelProperty(position = 145, value = "汇率")
    private Double exchangeRate;

    @ApiModelProperty(position = 150, value = "申请总金额", required = true)
    @NotNull
    private Double total;

    @ApiModelProperty(position = 160, value = "备注")
    @Size(max = 500)
    private String remark;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
