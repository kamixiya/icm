package com.kamixiya.icm.model.content.contract;

import com.kamixiya.icm.model.content.base.PageQueryOptionDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 合同订立查询信息
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Setter
@Getter
@AllArgsConstructor
public class ContractConclusionQueryOptionDTO {
    @ApiModelProperty(position = 100, value = "分页查询参数")
    private PageQueryOptionDTO pageQueryOption;

    @ApiModelProperty(position = 200, value = "状态类型,查询类型", required = true)
    @NotNull
    private StateType stateType;

    @ApiModelProperty(position = 210, value = "是否重大合同")
    private Boolean important;

    @ApiModelProperty(position = 220, value = "合同名称,支持模糊查询")
    private String name;

    @ApiModelProperty(position = 230, value = "合同年度")
    private String year;

    @ApiModelProperty(position = 240, value = "是否关联采购")
    private Boolean purchase;

    @ApiModelProperty(position = 250, value = "申请时间，起始日期，区间查询")
    private Date beginDate;

    @ApiModelProperty(position = 260, value = "申请时间，结束日期，区间查询")
    private Date endDate;

    @ApiModelProperty(position = 270, value = "申请单位")
    private String unitId;

    @ApiModelProperty(position = 280, value = "申请部门")
    private String departmentId;

    @ApiModelProperty(position = 290, value = "申请人名称，支持模糊查询")
    private String declarerName;

    @ApiModelProperty(position = 300, value = "合同订立ID")
    private String conclusionId;

}
