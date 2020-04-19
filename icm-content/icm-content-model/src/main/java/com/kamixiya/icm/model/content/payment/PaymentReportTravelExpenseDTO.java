package com.kamixiya.icm.model.content.payment;

import com.kamixiya.icm.model.security.user.UserBaseDTO;
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
@ApiModel(description = "事前资金申请——差旅费详细信息")
@Getter
@Setter
@ToString
public class PaymentReportTravelExpenseDTO extends PaymentReportTravelExpenseBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 700, value = "出差人员")
    private List<UserBaseDTO> users;

    @ApiModelProperty(position = 710, value = "地区")
    private String regional;

    @ApiModelProperty(position = 715, value = "出发地点")
    private String startArea;

    @ApiModelProperty(position = 720, value = "费用详情")
    private List<PaymentReportTravelDetailDTO> paymentDetails;
}
