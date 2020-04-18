package com.kamixiya.icm.model.content.revenue.opening;


import com.kamixiya.icm.model.base.SystemFileDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * BankAccountOpeningAttachDTO
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户开户申请附件详细信息")
@Getter
@Setter
@ToString
public class BankAccountOpeningAttachDTO extends BankAccountOpeningAttachBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 500, value = "附件")
    private List<SystemFileDTO> files;
}

