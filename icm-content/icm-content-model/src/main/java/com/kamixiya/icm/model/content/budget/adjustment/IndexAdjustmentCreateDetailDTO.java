package com.kamixiya.icm.model.content.budget.adjustment;

import com.kamixiya.icm.model.content.budget.indexLibrary.IndexLibraryDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "指标（预算）调整新增指标明细详细信息")
@Getter
@Setter
@ToString
public class IndexAdjustmentCreateDetailDTO extends IndexAdjustmentCreateDetailBaseDTO {
    @ApiModelProperty(position = 500, value = "指标库,预算调整备案后生成")
    private IndexLibraryDTO index;

    @ApiModelProperty(position = 530, value = "所属部门")
    private OrganizationBaseDTO department;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
