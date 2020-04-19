package com.kamixiya.icm.model.content.contract.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * ContractParticipantBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立——参与方基本信息")
@Getter
@Setter
@ToString
class ContractParticipantBaseDTO {
    @ApiModelProperty(position = 110, value = "参与方名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String name;

    @ApiModelProperty(position = 120, value = "单位名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String unitName;

    @ApiModelProperty(position = 130, value = "经办人", required = true)
    @NotBlank
    @Size(max = 50)
    private String trustees;

    @ApiModelProperty(position = 140, value = "联系方式", required = true)
    @NotBlank
    @Size(max = 50)
    private String phone;
}
