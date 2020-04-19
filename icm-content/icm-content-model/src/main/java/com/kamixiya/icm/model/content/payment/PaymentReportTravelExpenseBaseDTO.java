package com.kamixiya.icm.model.content.payment;

import com.kamixiya.icm.persistence.content.entity.payment.AccommodateType;
import com.kamixiya.icm.persistence.content.entity.payment.TravelReason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——差旅费基本信息")
@Getter
@Setter
@ToString
class PaymentReportTravelExpenseBaseDTO {
    @ApiModelProperty(position = 110, value = "总人数", required = true)
    @NotNull
    private Integer participantNumber;

    @ApiModelProperty(position = 120, value = "开始时间", required = true)
    @NotNull
    private Date beginDate;

    @ApiModelProperty(position = 130, value = "结束时间", required = true)
    @NotNull
    private Date endDate;

    @ApiModelProperty(position = 140, value = "天数", required = true)
    @NotNull
    private Integer duration;

    @ApiModelProperty(position = 150, value = "接待单位是否承担食宿", required = true)
    @NotNull
    private AccommodateType accommodateType;

    @ApiModelProperty(position = 160, value = "是否包车", required = true)
    @NotNull
    private Boolean charteredBus;

    @ApiModelProperty(position = 170, value = "出差事由", required = true)
    @NotNull
    private TravelReason travelReason;

    @ApiModelProperty(position = 180, value = "乘坐飞机理由")
    @Size(max = 1000)
    private String ridePlaneReason;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
