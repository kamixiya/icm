package com.kamixiya.icm.model.content.revenue.Registration;

import com.kamixiya.icm.model.organization.organization.OrganizationBaseDTO;
import com.kamixiya.icm.model.security.user.UserBaseDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * icm-server.com.matech.icm.model.revenue.nontax
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "银行账户登记详细信息")
@Getter
@Setter
@ToString
public class BankAccountRegistrationDTO extends BankAccountRegistrationBaseDTO {
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入")
    private String id;

    @ApiModelProperty(position = 110, value = "状态")
    private StateType state;

    @ApiModelProperty(position = 130, value = "申报单位", required = true)
    private OrganizationBaseDTO unit;

    @ApiModelProperty(position = 140, value = "申报部门", required = true)
    private OrganizationBaseDTO department;

    @ApiModelProperty(position = 140, value = "申报人", required = true)
    private UserBaseDTO registrant;

    @ApiModelProperty(position = 300, value = "附件信息")
    private List<BankAccountRegistrationAttachDTO> bankAccountRegistrationAttaches;

    @ApiModelProperty(position = 900, value = "创建数据的用户ID，系统根据token自动填写", required = true)
    private UserBaseDTO createUser;

    @ApiModelProperty(position = 910, value = "创建数据的时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date createdTime;

    @ApiModelProperty(position = 920, value = "最后修改者的ID，系统根据token自动填写", required = true)
    private UserBaseDTO lastModifyUser;

    @ApiModelProperty(position = 930, value = "最后的修收时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date lastModifiedTime;
}
