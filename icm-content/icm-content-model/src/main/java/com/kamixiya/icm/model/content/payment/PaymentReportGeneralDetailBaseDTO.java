package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——一般经费事前资金详细信息")
@Getter
@Setter
@ToString
public class PaymentReportGeneralDetailBaseDTO {
    @ApiModelProperty(position = 150, value = "申请金额（元）", required = true)
    @NotNull
    private Double amount;

    @ApiModelProperty(position = 160, value = "用途")
    @Size(max = 500)
    private String useType;

}
