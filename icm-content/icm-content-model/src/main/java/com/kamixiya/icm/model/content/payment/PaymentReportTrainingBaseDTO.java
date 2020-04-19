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
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——培训费基本信息")
@Getter
@Setter
@ToString
class PaymentReportTrainingBaseDTO {
    @ApiModelProperty(position = 120, value = "培训班名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String name;

    @ApiModelProperty(position = 130, value = "培训类型，对应字典表类型: PROJECT_TRAINING_TYPE", required = true)
    @NotBlank
    private String type;

    @ApiModelProperty(position = 140, value = "是否定点", required = true)
    @NotNull
    private Boolean appoint;

    @ApiModelProperty(position = 150, value = "培训地点，非定点时非空")
    @Size(max = 500)
    private String place;

    @ApiModelProperty(position = 160, value = "培训人数", required = true)
    @NotNull
    private Integer participantNumber;

    @ApiModelProperty(position = 170, value = "开始时间", required = true)
    @NotNull
    private Date beginDate;

    @ApiModelProperty(position = 180, value = "结束时间", required = true)
    @NotNull
    private Date endDate;

    @ApiModelProperty(position = 190, value = "天数", required = true)
    @NotNull
    private Integer duration;

    @ApiModelProperty(position = 210, value = "申请金额合计")
    private Double total;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
