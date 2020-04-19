package com.kamixiya.icm.model.content.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * PurchaseReportBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "采购申请基本信息")
@Getter
@Setter
@ToString
public class PurchaseReportBaseDTO {

    @ApiModelProperty(position = 100, value = "ID，新建时无需输入")
    private String id;

    @ApiModelProperty(position = 110, value = "申请单号", required = true)
    @NotBlank
    @Size(max = 50)
    private String code;

    @ApiModelProperty(position = 120, value = "申请日期", required = true, example = "2019-10-25")
    @NotNull
    private Date applyDate;

    @ApiModelProperty(position = 150, value = "业务标题", required = true)
    @Size(max = 500)
    private String title;

    @ApiModelProperty(position = 160, value = "采购事由")
    @Size(max = 500)
    private String reason;

    @ApiModelProperty(position = 170, value = "业务依据及内容")
    @Size(max = 500)
    private String basis;

    @ApiModelProperty(position = 180, value = "采购本年支出总金额（元）")
    private Double currentYearTotal;

    @ApiModelProperty(position = 190, value = "采购总金额（元）")
    private Double total;

}
