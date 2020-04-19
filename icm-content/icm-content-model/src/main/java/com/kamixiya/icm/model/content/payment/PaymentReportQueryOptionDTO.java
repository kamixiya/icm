package com.kamixiya.icm.model.content.payment;

import com.kamixiya.icm.model.content.base.PageQueryOptionDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.payment.ExpenseType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 事前资金申请查询信息
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Setter
@Getter
@AllArgsConstructor
public class PaymentReportQueryOptionDTO {
    @ApiModelProperty(position = 100, value = "分页查询参数")
    private PageQueryOptionDTO pageQueryOption;

    @ApiModelProperty(position = 200, value = "状态类型,查询类型", required = true)
    @NotNull
    private StateType stateType;

    @ApiModelProperty(position = 210, value = "申请单位ID")
    private String unitId;

    @ApiModelProperty(position = 220, value = "申请部门ID")
    private String departmentId;

    @ApiModelProperty(position = 230, value = "申请人姓名，支持模糊查询")
    private String declarerName;

    @ApiModelProperty(position = 240, value = "标题,支持模糊查询")
    private String title;

    @ApiModelProperty(position = 250, value = "申请金额")
    private Double beginAmount;

    @ApiModelProperty(position = 250, value = "申请金额")
    private Double endAmount;

    @ApiModelProperty(position = 260, value = "申请起始日期，区间查询")
    private Date beginDate;

    @ApiModelProperty(position = 270, value = "申请结束日期，区间查询")
    private Date endDate;

    @ApiModelProperty(position = 280, value = "费用类型")
    private ExpenseType expenseType;

    @ApiModelProperty(position = 290, value = "采购申请单单号")
    @NotNull
    private String code;

    @ApiModelProperty(position = 300, value = "采购申请id")
    private String purchaseReportId;

}
