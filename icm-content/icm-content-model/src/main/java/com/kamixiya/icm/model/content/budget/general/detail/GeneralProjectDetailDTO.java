package com.kamixiya.icm.model.content.budget.general.detail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "项目明细编辑信息")
@Getter
@Setter
@ToString
public class GeneralProjectDetailDTO extends GeneralProjectDetailBaseDTO{
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入")
    private String id;

    @ApiModelProperty(position = 600, value = "排序")
    private Integer showOrder;
}

