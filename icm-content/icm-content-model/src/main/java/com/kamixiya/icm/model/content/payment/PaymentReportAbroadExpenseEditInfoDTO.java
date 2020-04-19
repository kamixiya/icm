package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**

 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——因公出国（境）编辑信息")
@Getter
@Setter
@ToString
public class PaymentReportAbroadExpenseEditInfoDTO extends PaymentReportAbroadExpenseBaseDTO {
    @ApiModelProperty(position = 500, value = "国家", required = true)
    @NotBlank
    private String country;

    @ApiModelProperty(position = 510, value = "地区")
    private String regional;

    @ApiModelProperty(position = 520, value = "支出详情", required = true)
    @NotEmpty
    private List<PaymentReportDetailEditInfoDTO> paymentDetails;
}
