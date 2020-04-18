package com.kamixiya.icm.model.content.budget.adjustment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "指标（预算）调整编辑信息")
@Getter
@Setter
@ToString
public class IndexAdjustmentEditInfoDTO extends IndexAdjustmentCreateInfoDTO {
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入")
    private String id;

}
