package com.kamixiya.icm.model.content.payment;

import com.kamixiya.icm.persistence.content.entity.payment.ExpenseType;
import com.kamixiya.icm.persistence.content.entity.payment.ReceptionType;
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
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请基本信息")
@Getter
@Setter
@ToString
public class PaymentReportBaseDTO {
    @ApiModelProperty(position = 105, value = "费用类型", required = true)
    @NotNull
    private ExpenseType expenseType;

    @ApiModelProperty(position = 110, value = "申请单号，自动生成", required = true)
    @NotBlank
    @Size(max = 50)
    private String code;

    @ApiModelProperty(position = 120, value = "申请时间", required = true, example = "2019-10-25")
    @NotNull
    private Date applyDate;

    @ApiModelProperty(position = 130, value = "是否关联采购,会议费资金申请时非空")
    private Boolean purchase;

    @ApiModelProperty(position = 140, value = "业务标题")
    @Size(max = 200)
    private String title;

    @ApiModelProperty(position = 145, value = "职称")
    @Size(max = 50)
    private String professionalTitle;

    @ApiModelProperty(position = 150, value = "接待类型，费用类型是公务接待时非空")
    private ReceptionType receptionType;

    @ApiModelProperty(position = 160, value = "贵宾人数，费用类型是公务接待时非空")
    private Integer guestNumber;

    @ApiModelProperty(position = 170, value = "陪客人数，费用类型是公务接待时非空")
    private Integer entertainNumber;

    @ApiModelProperty(position = 180, value = "用车开始时间，费用类型是公务用车事前资金时非空")
    private Date beginDate;

    @ApiModelProperty(position = 190, value = "用车结束时间，费用类型是公务用车事前资金时非空")
    private Date endDate;

    @ApiModelProperty(position = 200, value = "预计产生费用，费用类型是公务用车事前资金时非空")
    private Double planAmount;

    @ApiModelProperty(position = 250, value = "请示内容")
    @Size(max = 500)
    private String content;

    @ApiModelProperty(position = 255, value = "用车事由")
    @Size(max = 500)
    private String carReason;

    @ApiModelProperty(position = 260, value = "备注")
    @Size(max = 500)
    private String remark;

    @ApiModelProperty(position = 270, value = "合计申请金额（元）")
    private Double total;

    @ApiModelProperty(position = 280, value = "合计申请金额（元），多类经费使用该字段")
    private Double multipleTotal;


}
