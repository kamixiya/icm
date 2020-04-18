package com.kamixiya.icm.model.content.budget.adjustment;

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
@ApiModel(description = "指标（预算）调整新增指标明细编辑信息")
@Getter
@Setter
@ToString
public class IndexAdjustmentCreateDetailEditInfoDTO extends IndexAdjustmentCreateDetailBaseDTO {

    @ApiModelProperty(position = 530, value = "所属部门ID", required = true)
    @NotBlank
    private String departmentId;
}
