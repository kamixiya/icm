package com.kamixiya.icm.model.content.budget.adjustment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "指标（预算）调整创建信息")
@Getter
@Setter
@ToString
public class IndexAdjustmentCreateInfoDTO extends IndexAdjustmentBaseDTO {
    @ApiModelProperty(position = 500, value = "申报单位ID", required = true)
    @NotBlank
    private String unitId;

    @ApiModelProperty(position = 510, value = "申报部门ID")
    @NotBlank
    private String departmentId;

    @ApiModelProperty(position = 515, value = "附件列表")
    private List<String> fileIds;

    @ApiModelProperty(position = 520, value = "指标调增信息，类型是指标调增和指标调剂时不能为空")
    private List<IndexAdjustmentDetailEditInfoDTO> increaseDetails;

    @ApiModelProperty(position = 530, value = "指标调减信息，类型是指标减和指标调剂时不能为空")
    private List<IndexAdjustmentDetailEditInfoDTO> reduceDetails;

    @ApiModelProperty(position = 540, value = "指标新增信息，类型是新增指标时不能为空")
    private List<IndexAdjustmentCreateDetailEditInfoDTO> createDetails;
}
