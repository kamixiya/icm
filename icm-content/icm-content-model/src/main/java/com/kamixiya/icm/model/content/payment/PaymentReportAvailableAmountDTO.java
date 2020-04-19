package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author Zhu Jie
 * @date 2019/12/12
 */
@ApiModel(description = "事前资金申请----可用余额")
@Getter
@Setter
@ToString
public class PaymentReportAvailableAmountDTO {

    @ApiModelProperty(position = 100, value = "采购指标可用余额")
    private Map<String, Double> purchaseReportIndexes;

    @ApiModelProperty(position = 110, value = "指标库可用余额")
    private Map<String, Double> indexLibraryIndexes;

    @ApiModelProperty(position = 120, value = "合同指标可用余额")
    private Map<String, Double> contractIndexes;

}
