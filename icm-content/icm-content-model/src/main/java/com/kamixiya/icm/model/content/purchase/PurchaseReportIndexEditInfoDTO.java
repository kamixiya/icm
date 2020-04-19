package com.kamixiya.icm.model.content.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * PurchaseReportIndexEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "采购申请——指标编辑信息")
@Getter
@Setter
@ToString
public class PurchaseReportIndexEditInfoDTO extends PurchaseReportIndexBaseDTO {
    @ApiModelProperty(position = 110, value = "指标ID", required = true)
    @NotBlank
    private String indexId;
}

