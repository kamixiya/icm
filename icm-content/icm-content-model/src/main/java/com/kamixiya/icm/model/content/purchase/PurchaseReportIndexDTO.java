package com.kamixiya.icm.model.content.purchase;

import com.kamixiya.icm.model.content.budget.indexLibrary.IndexLibraryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * PurchaseReportIndexDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "采购申请——指标详细信息")
@Getter
@Setter
@ToString
public class PurchaseReportIndexDTO extends PurchaseReportIndexBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 110, value = "指标")
    private IndexLibraryDTO index;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}

