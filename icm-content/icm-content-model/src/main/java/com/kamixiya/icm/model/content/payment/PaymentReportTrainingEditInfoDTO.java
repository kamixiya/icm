package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——培训费编辑信息")
@Getter
@Setter
@ToString
public class PaymentReportTrainingEditInfoDTO extends PaymentReportTrainingBaseDTO{
    @ApiModelProperty(position = 500, value = "供应商，是定点时非空")
    private String supplier;

    @ApiModelProperty(position = 510, value = "师资费", required = true)
    @NotEmpty
    private List<PaymentReportTeacherEditInfoDTO> teachers;

    @ApiModelProperty(position = 520, value = "支出详情", required = true)
    @NotEmpty
    private List<PaymentReportDetailEditInfoDTO> paymentDetails;
}
