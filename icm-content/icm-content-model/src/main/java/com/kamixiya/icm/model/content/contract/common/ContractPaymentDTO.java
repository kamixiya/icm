package com.kamixiya.icm.model.content.contract.common;

import com.kamixiya.icm.persistence.content.entity.contract.ContractPaymentState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ContractPaymentDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立——付款详细信息")
@Getter
@Setter
@ToString
public class ContractPaymentDTO extends ContractPaymentBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 110, value = "支付状态")
    private ContractPaymentState paymentState;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}
