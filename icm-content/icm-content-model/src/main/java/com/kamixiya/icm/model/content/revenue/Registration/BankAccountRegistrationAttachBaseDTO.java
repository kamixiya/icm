package com.kamixiya.icm.model.content.revenue.Registration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * icm-server.com.matech.icm.model.revenue.nontax
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户登记附件基础信息")
@Getter
@Setter
@ToString
class BankAccountRegistrationAttachBaseDTO {
    @ApiModelProperty(position = 110, value = "附件名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String name;
}
