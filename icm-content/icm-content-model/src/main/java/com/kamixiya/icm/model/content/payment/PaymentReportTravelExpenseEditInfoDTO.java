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
@ApiModel(description = "事前资金申请——差旅费编辑信息")
@Getter
@Setter
@ToString
public class PaymentReportTravelExpenseEditInfoDTO extends PaymentReportTravelExpenseBaseDTO {
    @ApiModelProperty(position = 500, value = "出差人员ID列表", required = true)
    @NotEmpty
    private List<String> userIds;

    @ApiModelProperty(position = 510, value = "地区", required = true)
    @NotBlank
    private String regional;

    @ApiModelProperty(position = 515, value = "出发地点", required = true)
    @NotBlank
    private String startArea;

    @ApiModelProperty(position = 520, value = "费用详情", required = true)
    @NotEmpty
    private List<PaymentReportTravelDetailEditInfoDTO> paymentDetails;
}
