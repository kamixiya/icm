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
@ApiModel(description = "事前资金申请——接待费用编辑信息")
@Getter
@Setter
@ToString
public class PaymentReportGuestExpenseEditInfoDTO extends PaymentReportGuestExpenseBaseDTO {

    @ApiModelProperty(position = 110, value = "费用类型", required = true)
    @NotBlank
    private String expenseTypeDetail;
}
