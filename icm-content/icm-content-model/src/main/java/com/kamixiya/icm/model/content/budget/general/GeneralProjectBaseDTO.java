package com.kamixiya.icm.model.content.budget.general;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "项目库基本信息")
@Getter
@Setter
@ToString
public class GeneralProjectBaseDTO {
    @ApiModelProperty(position = 105, value = "预算年度", required = true)
    @NotBlank
    @Size(max = 4)
    private String year;

    @ApiModelProperty(position = 110, value = "申报日期", required = true, example = "2019-9-25")
    @NotNull
    private Date applyDate;

    @ApiModelProperty(position = 120, value = "项目编码", required = true)
    @NotBlank
    @Size(max = 20)
    private String code;

    @ApiModelProperty(position = 130, value = "项目名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String name;

    @ApiModelProperty(position = 140, value = "申报类型,对于字典表类型: PROJECT_APPLY_TYPE", required = true)
    @NotBlank
    private String applyType;

    @ApiModelProperty(position = 150, value = "项目类别,对于字典表类型: PROJECT_TYPE", required = true)
    @NotBlank
    private String projectType;

    @ApiModelProperty(position = 160, value = "项目类别明细,对于字典表类型: PROJECT_DETAILED_TYPE", required = true)
    @NotBlank
    private String projectDetailedType;

    @ApiModelProperty(position = 170, value = "项目属性,对于字典表类型: PROJECT_PROPERTY")
    private String projectProperty;

    @ApiModelProperty(position = 180, value = "项目开始时间", example = "2019-9-25 22:27:39", required = true)
    @NotNull
    private Date beginTime;

    @ApiModelProperty(position = 180, value = "项目结束时间", example = "2019-9-25 22:27:39")
    @NotNull
    private Date endTime;

    @ApiModelProperty(position = 190, value = "重要内容")
    private String importantContent;

    @ApiModelProperty(position = 200, value = "主管部门联系人")
    @Size(max = 200)
    private String administrativeDepartmentLink;

    @ApiModelProperty(position = 210, value = "主管部门联系电话")
    @Size(max = 50)
    private String administrativeDepartmentTelephone;

    @ApiModelProperty(position = 220, value = "项目负责人")
    @Size(max = 200)
    private String projectLeader;

    @ApiModelProperty(position = 230, value = "项目负责人联系电话")
    @Size(max = 50)
    private String projectLeaderTelephone;

    @ApiModelProperty(position = 240, value = "项目负责人手机号码")
    @Size(max = 50)
    private String projectLeaderPhone;
}

