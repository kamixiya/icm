package com.kamixiya.icm.model.revenue.opening;

import com.kamixiya.icm.model.base.PageQueryOptionDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * BankAccountOpeningQueryOptionDTO
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@Setter
@AllArgsConstructor
@ApiModel(description = "银行账户开户申请条件")
public class BankAccountOpeningQueryOptionDTO {
    @ApiModelProperty(position = 100, value = "分页查询参数")
    private PageQueryOptionDTO pageQueryOption;

    @ApiModelProperty(position = 300, value = "申请单位")
    private String unitName;

    @ApiModelProperty(position = 400, value = "申请部门")
    private String departmentName;

    @ApiModelProperty(position = 500, value = "申请人")
    private String declarerName;

    @ApiModelProperty(position = 600, value = "账户性质")
    private String accountProperty;

    @ApiModelProperty(position = 700, value = "开户银行")
    private String nameOfBank;

    @ApiModelProperty(position = 800, value = "开户银行全称")
    private String fullNameOfBank;
}

