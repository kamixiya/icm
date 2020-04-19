package com.kamixiya.icm.model.content.revenue.Registration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * icm-server.com.matech.icm.model.revenue.nontax
 *
 * @author fengjinghua
 * @date 2019/11/12
 */
@ApiModel(description = "银行账户登记修改信息")
@Getter
@Setter
@ToString
public class BankAccountRegistrationEditInfoDTO extends BankAccountRegistrationCreateInfoDTO {
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入", required = true)
    private String id;

}
