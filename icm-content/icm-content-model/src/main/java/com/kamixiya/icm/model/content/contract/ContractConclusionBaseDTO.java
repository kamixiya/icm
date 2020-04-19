package com.kamixiya.icm.model.content.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * ContractConclusionBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立基本信息")
@Getter
@Setter
@ToString
class ContractConclusionBaseDTO {
    @ApiModelProperty(position = 110, value = "申请单号，自动生成", required = true)
    @NotBlank
    @Size(max = 50)
    private String code;

    @ApiModelProperty(position = 115, value = "合同编号，手输", required = true)
    @NotBlank
    @Size(max = 50)
    private String contractNo;

    @ApiModelProperty(position = 120, value = "申请日期", required = true, example = "2019-10-25")
    @NotNull
    private Date applyDate;

    @ApiModelProperty(position = 130, value = "合同年度，只读", required = true)
    @NotBlank
    @Size(max = 4)
    private String year;

    @ApiModelProperty(position = 140, value = "合同名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String name;

    @ApiModelProperty(position = 150, value = "合同开始时间", required = true)
    @NotNull
    private Date beginDate;

    @ApiModelProperty(position = 160, value = "合同结束时间", required = true)
    @NotNull
    private Date endDate;

    @ApiModelProperty(position = 170, value = "是否为重大合同", required = true)
    @NotNull
    private Boolean important;

    @ApiModelProperty(position = 180, value = "是否关联采购", required = true)
    @NotNull
    private Boolean purchase;

    @ApiModelProperty(position = 190, value = "合同签订依据及主要内容")
    @Size(max = 2000)
    private String basis;

    @ApiModelProperty(position = 200, value = "备注")
    @Size(max = 2000)
    private String remark;

    @ApiModelProperty(position = 210, value = "合同总额(元)")
    private Double total;

    @ApiModelProperty(position = 220, value = "申请金额合计")
    private Double applyTotal;
}
