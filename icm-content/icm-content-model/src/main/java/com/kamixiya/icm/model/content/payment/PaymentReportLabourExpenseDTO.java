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
@ApiModel(description = "事前资金申请——劳务费详细信息")
@Getter
@Setter
@ToString
public class PaymentReportLabourExpenseDTO extends PaymentReportLabourExpenseBaseDTO {

    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 700, value = "支出类型")
    private String expenseTypeDetail;

}
