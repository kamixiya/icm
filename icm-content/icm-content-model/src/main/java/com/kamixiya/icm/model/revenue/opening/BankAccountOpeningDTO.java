package com.kamixiya.icm.model.revenue.opening;

import com.kamixiya.icm.model.organization.organization.OrganizationBaseDTO;
import com.kamixiya.icm.model.security.user.UserBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * BankAccountOpeningDTO
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户开户申请信息")
@Getter
@Setter
@ToString
public class BankAccountOpeningDTO extends BankAccountOpeningBaseDTO {
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入")
    private String id;

    @ApiModelProperty(position = 120, value = "流程实例ID")
    private String instanceId;

    @ApiModelProperty(position = 130, value = "申请单位", required = true)
    private OrganizationBaseDTO unit;

    @ApiModelProperty(position = 140, value = "申请部门", required = true)
    private OrganizationBaseDTO department;

    @ApiModelProperty(position = 150, value = "申请人", required = true)
    private UserBaseDTO declarer;

    @ApiModelProperty(position = 230, value = "附件信息")
    private List<BankAccountOpeningAttachDTO> bankAccountOpeningAttaches;

    @ApiModelProperty(position = 900, value = "创建数据的用户ID，系统根据token自动填写", required = true)
    private UserBaseDTO createUser;

    @ApiModelProperty(position = 910, value = "创建数据的时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date createdTime;

    @ApiModelProperty(position = 920, value = "最后修改者的ID，系统根据token自动填写", required = true)
    private UserBaseDTO lastModifyUser;

    @ApiModelProperty(position = 930, value = "最后的修收时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date lastModifiedTime;
}
