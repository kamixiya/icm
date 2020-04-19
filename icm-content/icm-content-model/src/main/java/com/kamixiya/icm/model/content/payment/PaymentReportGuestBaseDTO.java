package com.kamixiya.icm.model.content.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**

 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请——接待对象基本信息")
@Getter
@Setter
@ToString
public class PaymentReportGuestBaseDTO {
    @ApiModelProperty(position = 110, value = "单位", required = true)
    @NotBlank
    @Size(max = 200)
    private String unitName;

    @ApiModelProperty(position = 120, value = "姓名", required = true)
    @NotBlank
    @Size(max = 200)
    private String name;

    @ApiModelProperty(position = 130, value = "职务", required = true)
    @NotBlank
    @Size(max = 200)
    private String duties;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
