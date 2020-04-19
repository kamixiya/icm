package com.kamixiya.icm.model.content.contract.common;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立——收款人银行账户编辑信息")
@Getter
@Setter
@ToString
public class ContractPayeeEditInfoDTO extends ContractPayeeBaseDTO {
}
