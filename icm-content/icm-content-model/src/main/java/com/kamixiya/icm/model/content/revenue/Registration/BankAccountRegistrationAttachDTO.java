package com.kamixiya.icm.model.content.revenue.Registration;

import com.kamixiya.icm.model.base.SystemFileDTO;
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
@ApiModel(description = "银行账户登记附件详细信息")
@Getter
@Setter
@ToString
public class BankAccountRegistrationAttachDTO extends BankAccountRegistrationAttachBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 500, value = "附件")
    private List<SystemFileDTO> files;
}
