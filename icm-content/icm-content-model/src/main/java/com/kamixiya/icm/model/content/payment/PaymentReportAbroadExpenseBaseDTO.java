package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**

 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——因公出国（境）基本信息")
@Getter
@Setter
@ToString
class PaymentReportAbroadExpenseBaseDTO {
    @ApiModelProperty(position = 120, value = "人数", required = true)
    @NotNull
    private Integer participantNumber;

    @ApiModelProperty(position = 130, value = "开始时间", required = true)
    @NotNull
    private Date beginDate;

    @ApiModelProperty(position = 140, value = "结束时间", required = true)
    @NotNull
    private Date endDate;

    @ApiModelProperty(position = 145, value = "结束时间", required = true)
    @NotNull
    private String currency;

    @ApiModelProperty(position = 150, value = "天数", required = true)
    @NotNull
    private Integer duration;

    @ApiModelProperty(position = 160, value = "申请金额合计")
    private Double total;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
