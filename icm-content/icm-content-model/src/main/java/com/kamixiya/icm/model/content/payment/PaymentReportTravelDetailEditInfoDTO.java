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
@ApiModel(description = "事前资金申请——差旅费——支出详情编辑信息")
@Getter
@Setter
@ToString
public class PaymentReportTravelDetailEditInfoDTO extends PaymentReportTravelDetailBaseDTO {
    @ApiModelProperty(position = 700, value = "支出类型", required = true)
    @NotBlank
    private String expenseTypeDetail;
}
