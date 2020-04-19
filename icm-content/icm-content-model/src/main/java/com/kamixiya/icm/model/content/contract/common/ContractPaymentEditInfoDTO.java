package com.kamixiya.icm.model.content.contract.common;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ContractPaymentDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立——付款编辑信息")
@Getter
@Setter
@ToString
public class ContractPaymentEditInfoDTO extends ContractPaymentBaseDTO {
}
