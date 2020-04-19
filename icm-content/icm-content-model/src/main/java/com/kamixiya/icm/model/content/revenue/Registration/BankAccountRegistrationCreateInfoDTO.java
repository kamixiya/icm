package com.kamixiya.icm.model.content.revenue.Registration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * icm-server.com.matech.icm.model.revenue.nontax
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户登记创建信息")
@Getter
@Setter
@ToString
public class BankAccountRegistrationCreateInfoDTO extends BankAccountRegistrationBaseDTO {
    @ApiModelProperty(position = 130, value = "申报单位", required = true)
    private String unitId;

    @ApiModelProperty(position = 140, value = "申报部门", required = true)
    private String departmentId;

    @ApiModelProperty(position = 300, value = "附件信息")
    private List<BankAccountRegistrationAttachEditInfoDTO> bankAccountRegistrationAttaches;
}
