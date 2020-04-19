package com.kamixiya.icm.model.content.revenue.Registration;

import com.kamixiya.icm.model.content.base.PageQueryOptionDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * icm-server.com.matech.icm.model.revenue.account
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@Getter
@Setter
@AllArgsConstructor
@ApiModel(description = "银行账户登记查询条件")
public class BankAccountRegistrationQueryOptionDTO {
    @ApiModelProperty(position = 100, value = "分页查询参数")
    private PageQueryOptionDTO pageQueryOption;

    @ApiModelProperty(position = 200, value = "状态类型,查询类型", required = true)
    @NotNull
    private StateType stateType;

    @ApiModelProperty(position = 300, value = "登记单位")
    private String unitName;

    @ApiModelProperty(position = 400, value = "登记部门")
    private String departmentName;

    @ApiModelProperty(position = 500, value = "登记人")
    private String registerName;

    @ApiModelProperty(position = 600, value = "登记时间(开始时间)")
    private Date beginTime;

    @ApiModelProperty(position = 700, value = "登记时间(结束时间)")
    private Date endTime;

    @ApiModelProperty(position = 800, value = "账户名称")
    private String accountName;

    @ApiModelProperty(position = 900, value = "账号")
    private String account;
}
