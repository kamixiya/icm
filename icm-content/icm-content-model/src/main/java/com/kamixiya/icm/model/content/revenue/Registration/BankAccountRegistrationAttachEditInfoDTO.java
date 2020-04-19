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
@ApiModel(description = "银行账户登记附件修改信息")
@Getter
@Setter
@ToString
public class BankAccountRegistrationAttachEditInfoDTO extends BankAccountRegistrationAttachBaseDTO {
    @ApiModelProperty(position = 500, value = "附件ID列表")
    private List<String> fileIds;
}
