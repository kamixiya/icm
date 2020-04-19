package com.kamixiya.icm.model.content.contract.common;

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
 * ContractPaymentBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立——付款基本信息")
@Getter
@Setter
@ToString
public class ContractPaymentBaseDTO {
    @ApiModelProperty(position = 110, value = "付款阶段", required = true)
    @NotBlank
    @Size(max = 100)
    private String stage;

    @ApiModelProperty(position = 120, value = "付款条件", required = true)
    @NotBlank
    @Size(max = 200)
    private String terms;

    @ApiModelProperty(position = 130, value = "付款比例(%),全部项之和为100%", required = true)
    @NotNull
    private Double rate;

    @ApiModelProperty(position = 140, value = "付款年份", required = true)
    @NotBlank
    @Size(max = 4)
    private String year;

    @ApiModelProperty(position = 150, value = "预付款时间", required = true)
    @NotNull
    private Date payDate;

    @ApiModelProperty(position = 160, value = "金额(元)", required = true)
    @NotNull
    private Double amount;
}
