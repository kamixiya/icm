package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——师资费基本信息")
@Getter
@Setter
@ToString
class PaymentReportTeacherBaseDTO {
    @ApiModelProperty(position = 110, value = "上课时间", required = true)
    @NotNull
    private Date classTime;

    @ApiModelProperty(position = 120, value = "课时", required = true)
    @NotNull
    private Integer lessonPeriod;

    @ApiModelProperty(position = 130, value = "授课内容")
    @Size(max = 500)
    private String content;

    @ApiModelProperty(position = 140, value = "职称")
    @Size(max = 50)
    private String professionalTitle;

    @ApiModelProperty(position = 150, value = "参考标准（元/人课时）", required = true)
    @NotNull
    private Double referenceStandard;

    @ApiModelProperty(position = 160, value = "申请金额（元/人课时）")
    private Double amount;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
