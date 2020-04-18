package com.kamixiya.icm.model.content.revenue.opening;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * BankAccountOpeningAttachBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户开户申请附件基础信息")
@Getter
@Setter
@ToString
class BankAccountOpeningAttachBaseDTO {
    @ApiModelProperty(position = 110, value = "附件名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String name;
}