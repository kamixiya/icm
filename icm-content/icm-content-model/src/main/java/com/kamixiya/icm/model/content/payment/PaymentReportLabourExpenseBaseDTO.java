package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——劳务费基本信息")
@Getter
@Setter
@ToString
class PaymentReportLabourExpenseBaseDTO extends PaymentReportDetailBaseDTO {
    @ApiModelProperty(position = 115, value = "人数", required = true)
    @NotNull
    private Integer participantNumber;

    @ApiModelProperty(position = 146, value = "天数", required = true)
    @NotNull
    private Integer duration;
}
