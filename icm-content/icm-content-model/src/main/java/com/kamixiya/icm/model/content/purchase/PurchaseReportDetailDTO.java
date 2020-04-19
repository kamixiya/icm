package com.kamixiya.icm.model.content.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * PurchaseReportDetailDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "采购申请——采购内容详细信息")
@Getter
@Setter
@ToString
public class PurchaseReportDetailDTO extends PurchaseReportDetailBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 510, value = "内容名称")
    private String purchaseCategory;


    @ApiModelProperty(position = 530, value = "指标信息")
    private List<PurchaseReportIndexDTO> indexes;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}

