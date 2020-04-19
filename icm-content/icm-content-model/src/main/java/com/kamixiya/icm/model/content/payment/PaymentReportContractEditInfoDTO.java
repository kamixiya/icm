package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——合同付款编辑信息")
@Getter
@Setter
@ToString
public class PaymentReportContractEditInfoDTO extends PaymentReportContractBaseDTO {
}
