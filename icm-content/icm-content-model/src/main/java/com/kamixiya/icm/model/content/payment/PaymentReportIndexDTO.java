package com.kamixiya.icm.model.content.payment;

import com.kamixiya.icm.model.content.budget.indexLibrary.IndexLibraryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * PaymentReportIndexDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——指标详细信息")
@Getter
@Setter
@ToString
public class PaymentReportIndexDTO extends PaymentReportIndexBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 110, value = "指标")
    private IndexLibraryDTO index;

    @ApiModelProperty(position = 730, value = "在途金额")
    private Double passageAmount;

    @ApiModelProperty(position = 740, value = "已报销金额")
    private Double paidAmount;

    @ApiModelProperty(position = 750, value = "释放金额")
    private Double releaseAmount;
}
