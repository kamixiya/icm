package com.kamixiya.icm.model.content.contract.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * ContractIndexBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立——指标基本信息")
@Getter
@Setter
@ToString
class ContractIndexBaseDTO {
    @ApiModelProperty(position = 110, value = "采购金额")
    private Double purchaseAmount;

    @ApiModelProperty(position = 120, value = "申请金额", required = true)
    @NotNull
    private Double amount;
}
