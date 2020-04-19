package com.kamixiya.icm.model.content.contract;

import com.kamixiya.icm.model.content.contract.common.ContractIndexDTO;
import com.kamixiya.icm.model.content.contract.common.ContractParticipantDTO;
import com.kamixiya.icm.model.content.contract.common.ContractPayeeDTO;
import com.kamixiya.icm.model.content.contract.common.ContractPaymentDTO;
import com.kamixiya.icm.model.content.purchase.AttachDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportBaseDTO;
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
 * ContractConclusionDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立详细信息")
@Getter
@Setter
@ToString
public class ContractConclusionDTO extends ContractConclusionBaseDTO {
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入")
    private String id;

    @ApiModelProperty(position = 110, value = "状态")
    private StateType state;

    @ApiModelProperty(position = 710, value = "单位")
    private OrganizationBaseDTO unit;

    @ApiModelProperty(position = 720, value = "申请部门")
    private OrganizationBaseDTO department;

    @ApiModelProperty(position = 730, value = "申请人")
    private UserBaseDTO declarer;

    @ApiModelProperty(position = 740, value = "关联采购备案单")
    private PurchaseReportBaseDTO purchaseReport;

    @ApiModelProperty(position = 750, value = "指标信息")
    private List<ContractIndexDTO> indexes;

    @ApiModelProperty(position = 760, value = "合同参与方信息")
    private List<ContractParticipantDTO> participants;

    @ApiModelProperty(position = 770, value = "付款信息")
    private List<ContractPaymentDTO> payments;

    @ApiModelProperty(position = 780, value = "收款人银行账户信息")
    private List<ContractPayeeDTO> payees;

    @ApiModelProperty(position = 790, value = "附件信息")
    private List<AttachDTO> attaches;

    @ApiModelProperty(position = 900, value = "创建数据的用户ID，系统根据token自动填写", required = true)
    private UserBaseDTO createUser;

    @ApiModelProperty(position = 910, value = "创建数据的时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date createdTime;

    @ApiModelProperty(position = 920, value = "最后修改者的ID，系统根据token自动填写", required = true)
    private UserBaseDTO lastModifyUser;

    @ApiModelProperty(position = 930, value = "最后的修收时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date lastModifiedTime;
}
