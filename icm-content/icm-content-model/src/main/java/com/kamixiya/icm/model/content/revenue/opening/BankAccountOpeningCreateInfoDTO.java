package com.kamixiya.icm.model.content.revenue.opening;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * BankAccountOpeningCreateInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户开户创建信息")
@Getter
@Setter
@ToString
public class BankAccountOpeningCreateInfoDTO extends BankAccountOpeningBaseDTO {
    @ApiModelProperty(position = 500, value = "单位ID", required = true)
    @NotBlank
    private String unitId;

    @ApiModelProperty(position = 510, value = "申请部门ID", required = true)
    @NotBlank
    private String departmentId;

    @ApiModelProperty(position = 520, value = "申请人Id", required = true)
    @NotBlank
    private String declarerId;

    @ApiModelProperty(position = 530, value = "非税附件列表")
    private List<BankAccountOpeningAttachEditInfoDTO> bankAccountOpeningAttaches;
}
