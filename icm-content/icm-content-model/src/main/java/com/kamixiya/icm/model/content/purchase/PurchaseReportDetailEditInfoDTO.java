package com.kamixiya.icm.model.content.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * PurchaseReportDetailEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "采购申请——采购内容编辑信息")
@Getter
@Setter
@ToString
public class PurchaseReportDetailEditInfoDTO extends PurchaseReportDetailBaseDTO {

    @ApiModelProperty(position = 510, value = "品名名称", required = true)
    @NotBlank
    private String purchaseDetail;

    @ApiModelProperty(position = 530, value = "指标信息")
    private List<PurchaseReportIndexEditInfoDTO> indexes;
}