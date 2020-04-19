package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**

 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——接待费用详细信息")
@Getter
@Setter
@ToString
public class PaymentReportGuestExpenseDTO extends PaymentReportGuestExpenseBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 110, value = "费用类型")
    private String expenseTypeDetail;
}
