package com.kamixiya.icm.model.content.budget.adjustment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "指标（预算）调整明细基本信息")
@Getter
@Setter
@ToString
class IndexAdjustmentDetailBaseDTO {
    @ApiModelProperty(position = 110, value = "指标总额", required = true)
    @NotNull
    private Double indexAmount;

    @ApiModelProperty(position = 120, value = "指标余额", required = true)
    @NotNull
    private Double availableAmount;

    @ApiModelProperty(position = 130, value = "占用金额", required = true)
    @NotNull
    private Double occupationAmount;

    @ApiModelProperty(position = 140, value = "调增金额", required = true)
    @NotNull
    private Double adjustAmount;


}
