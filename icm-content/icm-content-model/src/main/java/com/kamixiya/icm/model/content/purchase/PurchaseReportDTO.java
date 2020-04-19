package com.kamixiya.icm.model.content.purchase;

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
 * PurchaseReportDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "采购申请详细信息")
@Getter
@Setter
@ToString
public class PurchaseReportDTO extends PurchaseReportBaseDTO {
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

    @ApiModelProperty(position = 740, value = "采购内容")
    private List<PurchaseReportDetailDTO> details;

    @ApiModelProperty(position = 760, value = "指标信息，品目采购为空")
    private List<PurchaseReportIndexDTO> indexes;

    @ApiModelProperty(position = 750, value = "采购附件")
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
