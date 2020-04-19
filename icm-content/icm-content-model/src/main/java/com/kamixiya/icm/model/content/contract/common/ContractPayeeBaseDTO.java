package com.kamixiya.icm.model.content.contract.common;

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
@ApiModel(description = "合同订立——收款人银行账户基本信息")
@Getter
@Setter
@ToString
public class ContractPayeeBaseDTO {
    @ApiModelProperty(position = 110, value = "单位名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String unitName;

    @ApiModelProperty(position = 120, value = "开户银行名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String bankName;

    @ApiModelProperty(position = 130, value = "银行账号", required = true)
    @NotBlank
    @Size(max = 100)
    private String account;
}
