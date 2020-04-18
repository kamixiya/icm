package com.kamixiya.icm.model.content.budget.general.detail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "项目明细及说明基本信息")
@Getter
@Setter
@ToString
public class GeneralProjectDetailBaseDTO {

    @ApiModelProperty(position = 100, value = "项目编号")
    @Size(max = 50)
    private String code;

    @ApiModelProperty(position = 110, value = "支出简介")
    @Size(max = 200)
    private String introduction;

    @ApiModelProperty(position = 120, value = "支出明细")
    private String detail;

    @ApiModelProperty(position = 130, value = "预算金额")
    private Double amount;

    @ApiModelProperty(position = 140, value = "预算依据及说明")
    @Size(max = 2000)
    private String basis;

    @ApiModelProperty(position = 150, value = "重要说明")
    @Size(max = 200)
    private String remark;
}
