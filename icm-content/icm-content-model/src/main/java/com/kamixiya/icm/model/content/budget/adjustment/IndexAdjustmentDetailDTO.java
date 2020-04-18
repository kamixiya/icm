package com.kamixiya.icm.model.content.budget.adjustment;

import com.kamixiya.icm.model.content.budget.indexLibrary.IndexLibraryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "指标（预算）调整明细详细信息")
@Getter
@Setter
@ToString
public class IndexAdjustmentDetailDTO extends IndexAdjustmentDetailBaseDTO {
    @ApiModelProperty(position = 500, value = "指标库")
    private IndexLibraryDTO indexLibrary;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
