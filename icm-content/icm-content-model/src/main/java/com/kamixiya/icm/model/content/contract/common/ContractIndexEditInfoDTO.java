package com.kamixiya.icm.model.content.contract.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * ContractIndexDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立——指标编辑信息")
@Getter
@Setter
@ToString
public class ContractIndexEditInfoDTO extends ContractIndexBaseDTO {
    @ApiModelProperty(position = 300, value = "关联的指标ID", required = true)
    @NotBlank
    private String indexId;
}
