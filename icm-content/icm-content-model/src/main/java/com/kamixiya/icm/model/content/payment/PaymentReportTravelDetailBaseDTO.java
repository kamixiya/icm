package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——差旅费——支出详情基本信息")
@Getter
@Setter
@ToString
class PaymentReportTravelDetailBaseDTO extends PaymentReportDetailBaseDTO {
    @ApiModelProperty(position = 10, value = "职级，对应字典表类型：USER_LEVEL", required = true)
    @NotBlank
    private String level;

    @ApiModelProperty(position = 20, value = "人数", required = true)
    @NotNull
    private Integer participantNumber;

    @ApiModelProperty(position = 30, value = "交通工具，对应字典表类型：TRANSPORTATION_FACILITY", required = true)
    @NotBlank
    private String transportationFacility;


}
