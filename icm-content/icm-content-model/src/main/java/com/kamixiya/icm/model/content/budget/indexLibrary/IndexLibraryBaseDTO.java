package com.kamixiya.icm.model.content.budget.indexLibrary;

import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "指标库基本信息")
@Getter
@Setter
@ToString
public class IndexLibraryBaseDTO {

    @ApiModelProperty(position = 100, value = "指标年份", required = true)
    @NotBlank
    @Size(max = 4)
    private String year;

    @ApiModelProperty(position = 120, value = "大项目名称（项目名称）", required = true)
    private String largeProjectName;

    @ApiModelProperty(position = 120, value = "大项目编号（项目编号）", required = true)
    private String largeProjectCode;

    @ApiModelProperty(position = 120, value = "项目名称（明细项目名称）", required = true)
    private String projectName;

    @ApiModelProperty(position = 120, value = "项目编号（明细项目编号）", required = true)
    private String projectCode;

    @ApiModelProperty(position = 120, value = "分配金额/批复金额,大于等于零", required = true)
    @NotNull
    private Double allocationAmount;

    @ApiModelProperty(position = 130, value = "占用金额,大于等于零（各个单据已审结完的金额，不包括在途金额）", required = true)
    @NotNull
    private Double occupationAmount;

    @ApiModelProperty(position = 130, value = "在途金额（各个单据正在审核中的金额）,大于等于零", required = true)
    @NotNull
    private Double passageAmount;

    @ApiModelProperty(position = 135, value = "预算调减在途金额")
    @NotNull
    private Double reducePassageAmount;

    @ApiModelProperty(position = 135, value = "调整金额,大于等于零", required = true)
    @NotNull
    private Double adjustmentAmount;

    @ApiModelProperty(position = 140, value = "可用金额（= 分配批复金额 + 调整金额 - 占用金额 - 在途金额 - 预算调减在途金额）", required = true)
    @NotNull
    private Double availableAmount;

    @ApiModelProperty(position = 160, value = "指标类型：APPROVAL：批复分配指标；APPEND：新增指标", required = true)
    @NotNull
    private IndexType indexType;
}

