package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**

 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——因公出国（境）详细信息")
@Getter
@Setter
@ToString
public class PaymentReportAbroadExpenseDTO extends PaymentReportAbroadExpenseBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 500, value = "国家")
    private String country;

    @ApiModelProperty(position = 510, value = "地区，国家下面有地区一定要选地区")
    private String regional;

    @ApiModelProperty(position = 520, value = "支出详情")
    private List<PaymentReportDetailDTO> paymentDetails;
}
