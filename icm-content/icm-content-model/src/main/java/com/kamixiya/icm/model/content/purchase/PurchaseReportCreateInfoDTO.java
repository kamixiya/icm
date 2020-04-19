package com.kamixiya.icm.model.content.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * PurchaseReportCreateInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "采购申请创建信息")
@Getter
@Setter
@ToString
public class PurchaseReportCreateInfoDTO extends PurchaseReportBaseDTO {
    @ApiModelProperty(position = 500, value = "单位ID", required = true)
    @NotBlank
    private String unitId;

    @ApiModelProperty(position = 510, value = "部门ID", required = true)
    @NotBlank
    private String departmentId;

    @ApiModelProperty(position = 520, value = "申请人Id", required = true)
    @NotBlank
    private String declarerId;

    @ApiModelProperty(position = 530, value = "采购内容")
    private List<PurchaseReportDetailEditInfoDTO> details;

    @ApiModelProperty(position = 550, value = "指标信息，品目采购为空")
    private List<PurchaseReportIndexEditInfoDTO> indexes;

    @ApiModelProperty(position = 580, value = "采购附件")
    private List<AttachEditInfoDTO> attaches;

}
