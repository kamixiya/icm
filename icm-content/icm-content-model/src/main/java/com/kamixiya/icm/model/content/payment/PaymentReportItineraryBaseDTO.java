package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**

 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——主要行程安排基本信息")
@Getter
@Setter
@ToString
class PaymentReportItineraryBaseDTO {
    @ApiModelProperty(position = 110, value = "项目", required = true)
    @NotBlank
    @Size(max = 200)
    private String projectName;

    @ApiModelProperty(position = 120, value = "时间", required = true)
    @NotNull
    private Date time;

    @ApiModelProperty(position = 130, value = "场所", required = true)
    @NotBlank
    @Size(max = 500)
    private String place;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
