package com.kamixiya.icm.model.content.revenue.opening;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * BankAccountOpeningEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户开户编辑信息")
@Getter
@Setter
@ToString
public class BankAccountOpeningEditInfoDTO extends BankAccountOpeningCreateInfoDTO {
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入", required = true)
    private String id;

}
