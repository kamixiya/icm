package com.kamixiya.icm.model.content.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ContractConclusionDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立编辑信息")
@Getter
@Setter
@ToString
public class ContractConclusionEditInfoDTO extends ContractConclusionCreateInfoDTO {
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入", required = true)
    private String id;
}
