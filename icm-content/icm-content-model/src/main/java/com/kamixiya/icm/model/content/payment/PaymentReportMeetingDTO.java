package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——会议费详细信息")
@Getter
@Setter
@ToString
public class PaymentReportMeetingDTO extends PaymentReportMeetingBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 500, value = "供应商")
    private String supplier;

    @ApiModelProperty(position = 510, value = "支出详情")
    private List<PaymentReportDetailDTO> paymentDetails;
}
