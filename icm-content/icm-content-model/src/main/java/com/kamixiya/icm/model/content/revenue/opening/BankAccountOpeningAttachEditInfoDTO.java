package com.kamixiya.icm.model.content.revenue.opening;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * BankAccountOpeningAttachEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户开户申请附件编辑信息")
@Getter
@Setter
@ToString
public class BankAccountOpeningAttachEditInfoDTO extends BankAccountOpeningAttachBaseDTO {
    @ApiModelProperty(position = 500, value = "附件ID列表")
    private List<String> fileIds;
}

