package com.kamixiya.icm.model.content.contract.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ContractParticipantDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立——参与方详细信息")
@Getter
@Setter
@ToString
public class ContractParticipantDTO extends ContractParticipantBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
