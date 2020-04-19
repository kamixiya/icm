package com.kamixiya.icm.model.content.payment;

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
@ApiModel(description = "事前资金申请——劳务费编辑信息")
@Getter
@Setter
@ToString
public class PaymentReportLabourExpenseEditInfoDTO extends PaymentReportLabourExpenseBaseDTO {
    @ApiModelProperty(position = 700, value = "支出类型ID", required = true)
    @NotBlank
    private String expenseTypeDetailId;
}
