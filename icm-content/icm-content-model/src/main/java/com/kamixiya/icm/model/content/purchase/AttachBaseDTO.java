package com.kamixiya.icm.model.content.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * AttachBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "附件基本信息")
@Getter
@Setter
@ToString
class AttachBaseDTO {
    @ApiModelProperty(position = 110, value = "附件类型,对应字典表各种分类", required = true)
    @NotBlank
    private String fileType;

    @ApiModelProperty(position = 120, value = "费用类型", required = true)
    @NotBlank
    private String expenseType;
}
