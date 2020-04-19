package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——支出详情信息")
@Getter
@Setter
@ToString
public class PaymentReportDetailDTO extends PaymentReportDetailBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 700, value = "支出类型")
    private String expenseTypeDetail;

    @ApiModelProperty(position = 730, value = "在途金额")
    private Double passageAmount;

    @ApiModelProperty(position = 740, value = "已报销金额")
    private Double paidAmount;
}
