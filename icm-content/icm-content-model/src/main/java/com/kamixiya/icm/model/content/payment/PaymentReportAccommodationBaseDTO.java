package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**

 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——住宿安排基本信息")
@Getter
@Setter
@ToString
class PaymentReportAccommodationBaseDTO {
    @ApiModelProperty(position = 110, value = "宾馆名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String hotelName;

    @ApiModelProperty(position = 120, value = "普通套间数", required = true)
    @NotNull
    private Integer generalNumber;

    @ApiModelProperty(position = 130, value = "标准间数", required = true)
    @NotNull
    private Integer standardNumber;

    @ApiModelProperty(position = 710, value = "排序", required = true)
    @NotNull
    private Integer showOrder;
}
