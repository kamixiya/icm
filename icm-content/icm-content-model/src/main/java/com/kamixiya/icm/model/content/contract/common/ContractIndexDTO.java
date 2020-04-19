package com.kamixiya.icm.model.content.contract.common;

import com.kamixiya.icm.model.content.budget.indexLibrary.IndexLibraryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ContractIndexDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立——指标详细信息")
@Getter
@Setter
@ToString
public class ContractIndexDTO extends ContractIndexBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 300, value = "关联的指标")
    private IndexLibraryDTO index;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;

    @ApiModelProperty(position = 720, value = "已支付金额")
    private Double paidAmount;

    @ApiModelProperty(position = 730, value = "在途金额")
    private Double passageAmount;

    @ApiModelProperty(position = 740, value = "采购申请余额/预算参考余额")
    private Double referenceBalance;
}
