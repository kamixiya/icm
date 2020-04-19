package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * PaymentReportIndexDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——指标编辑信息")
@Getter
@Setter
@ToString
public class PaymentReportIndexEditInfoDTO extends PaymentReportIndexBaseDTO {
    @ApiModelProperty(position = 110, value = "指标ID", required = true)
    @NotBlank
    private String indexId;
}
