package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**

 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——住宿安排详细信息")
@Getter
@Setter
@ToString
public class PaymentReportAccommodationDTO extends PaymentReportAccommodationBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

}
