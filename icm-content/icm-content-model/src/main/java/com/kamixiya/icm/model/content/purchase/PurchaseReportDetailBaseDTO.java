package com.kamixiya.icm.model.content.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * PurchaseReportDetailBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "采购申请——采购内容基本信息")
@Getter
@Setter
@ToString
public class PurchaseReportDetailBaseDTO {

    @ApiModelProperty(position = 100, value = "规格型号", required = true)
    @NotBlank
    @Size(max = 200)
    private String spec;

    @ApiModelProperty(position = 110, value = "参考单价", required = true)
    @NotNull
    private Double price;

    @ApiModelProperty(position = 110, value = "合计数量", required = true)
    private Double quantityTotal;


    @ApiModelProperty(position = 150, value = "采购方式", required = true)
    @NotBlank
    private String purchaseType;

    @ApiModelProperty(position = 170, value = "采购金额合计")
    private Double total;

}
