package com.kamixiya.icm.model.content.budget.adjustment;

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
@ApiModel(description = "指标（预算）调整新增指标明细基本信息")
@Getter
@Setter
@ToString
class IndexAdjustmentCreateDetailBaseDTO {
    @ApiModelProperty(position = 100, value = "项目名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String projectName;

    @ApiModelProperty(position = 110, value = "大项目名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String largeProjectName;

    @ApiModelProperty(position = 120, value = "大项目编号", required = true)
    @NotBlank
    @Size(max = 200)
    private String largeProjectCode;

    @ApiModelProperty(position = 130, value = "指标总额", required = true)
    @NotNull
    private Double indexAmount;
}
