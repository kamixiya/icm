package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——师资费编辑信息")
@Getter
@Setter
@ToString
public class PaymentReportTeacherEditInfoDTO extends PaymentReportTeacherBaseDTO {
    @ApiModelProperty(position = 510, value = "专家")
    private String expert;
}
