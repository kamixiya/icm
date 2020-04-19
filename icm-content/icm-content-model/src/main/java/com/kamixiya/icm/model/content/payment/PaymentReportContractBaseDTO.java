package com.kamixiya.icm.model.content.payment;

import com.kamixiya.icm.model.content.contract.common.ContractPaymentBaseDTO;
import com.kamixiya.icm.persistence.content.entity.contract.ContractPaymentState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——合同付款基本信息")
@Getter
@Setter
@ToString
class PaymentReportContractBaseDTO extends ContractPaymentBaseDTO {
    @ApiModelProperty(position = 10, value = "合同付款信息ID", required = true)
    @NotBlank
    private String contractPaymentId;

    @ApiModelProperty(position = 110, value = "支付状态")
    private ContractPaymentState paymentState;

    @ApiModelProperty(position = 200, value = "申请支付，一次只能申请一个支付")
    private Boolean requestPayment;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
