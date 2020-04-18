package com.kamixiya.icm.model.content.budget.adjustment;

import com.kamixiya.icm.persistence.content.entity.budget.adjustment.AdjustType;
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
@ApiModel(description = "指标（预算）调整基本信息")
@Getter
@Setter
@ToString
class IndexAdjustmentBaseDTO {
    @ApiModelProperty(position = 110, value = "调剂编号", required = true)
    @NotBlank
    @Size(max = 50)
    private String code;

    @ApiModelProperty(position = 120, value = "调剂时间", required = true, example = "2019-10-16")
    @NotNull
    private Date adjustDate;

    @ApiModelProperty(position = 130, value = "项目编号", required = true)
    @NotBlank
    private String projectCode;

    @ApiModelProperty(position = 140, value = "项目名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String projectName;

    @ApiModelProperty(position = 150, value = "调剂类型", required = true)
    @NotNull
    private AdjustType adjustType;

    @ApiModelProperty(position = 160, value = "调剂依据")
    @Size(max = 500)
    private String basis;

    @ApiModelProperty(position = 170, value = "调剂总额", required = true)
    @NotNull
    private Double total;
}
