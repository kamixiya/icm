package com.kamixiya.icm.model.content.contract;

import com.kamixiya.icm.model.content.contract.common.ContractIndexEditInfoDTO;
import com.kamixiya.icm.model.content.contract.common.ContractParticipantEditInfoDTO;
import com.kamixiya.icm.model.content.contract.common.ContractPayeeEditInfoDTO;
import com.kamixiya.icm.model.content.contract.common.ContractPaymentEditInfoDTO;
import com.kamixiya.icm.model.content.purchase.AttachEditInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * ContractConclusionDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "合同订立创建信息")
@Getter
@Setter
@ToString
public class ContractConclusionCreateInfoDTO extends ContractConclusionBaseDTO {
    @ApiModelProperty(position = 500, value = "单位ID", required = true)
    @NotBlank
    private String unitId;

    @ApiModelProperty(position = 510, value = "部门ID", required = true)
    @NotBlank
    private String departmentId;

    @ApiModelProperty(position = 520, value = "申请人Id", required = true)
    @NotBlank
    private String declarerId;

    @ApiModelProperty(position = 530, value = "关联采购备案单ID,是否关联采购为是时非空")
    private String purchaseReportId;

    @ApiModelProperty(position = 750, value = "指标信息", required = true)
    @NotEmpty
    private List<ContractIndexEditInfoDTO> indexes;

    @ApiModelProperty(position = 760, value = "合同参与方信息", required = true)
    @NotEmpty
    private List<ContractParticipantEditInfoDTO> participants;

    @ApiModelProperty(position = 770, value = "付款信息", required = true)
    @NotEmpty
    private List<ContractPaymentEditInfoDTO> payments;

    @ApiModelProperty(position = 780, value = "收款人银行账户信息", required = true)
    @NotEmpty
    private List<ContractPayeeEditInfoDTO> payees;

    @ApiModelProperty(position = 790, value = "附件信息")
    private List<AttachEditInfoDTO> attaches;
}
