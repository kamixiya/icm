package com.kamixiya.icm.model.content.payment;

import com.kamixiya.icm.model.content.contract.ContractConclusionDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请信息")
@Getter
@Setter
@ToString
public class PaymentReportInfoDTO extends PaymentReportBaseDTO {
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入")
    private String id;

    @ApiModelProperty(position = 800, value = "可用金额合计")
    private Double availableTotal;

    @ApiModelProperty(position = 732, value = "关联采购备案单（会议费）")
    private PurchaseReportDTO purchaseReport;

    @ApiModelProperty(position = 734, value = "合同,合同费用时非空")
    private ContractConclusionDTO contract;

    @ApiModelProperty(position = 736, value = "合同关联采购")
    private PurchaseReportDTO contractPurchaseReport;

    @ApiModelProperty(position = 736, value = "关联采购（非会议费）")
    private PurchaseReportDTO purchaseReport2;
}
