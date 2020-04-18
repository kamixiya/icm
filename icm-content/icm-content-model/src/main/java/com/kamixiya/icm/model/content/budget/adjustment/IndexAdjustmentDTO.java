package com.kamixiya.icm.model.content.budget.adjustment;

import com.kamixiya.icm.model.base.SystemFileDTO;
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
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "指标（预算）调整详细信息")
@Getter
@Setter
@ToString
public class IndexAdjustmentDTO extends IndexAdjustmentBaseDTO {
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入")
    private String id;
    @ApiModelProperty(position = 500, value = "申报单位")
    private OrganizationBaseDTO unit;

    @ApiModelProperty(position = 510, value = "申报部门")
    private OrganizationBaseDTO department;

    @ApiModelProperty(position = 515, value = "附件")
    private List<SystemFileDTO> files;

    @ApiModelProperty(position = 520, value = "指标调增信息")
    private List<IndexAdjustmentDetailDTO> increaseDetails;

    @ApiModelProperty(position = 530, value = "指标调减信息")
    private List<IndexAdjustmentDetailDTO> reduceDetails;

    @ApiModelProperty(position = 540, value = "指标新增信息，新增指标时不能为空")
    private List<IndexAdjustmentCreateDetailDTO> createDetails;

    @ApiModelProperty(position = 900, value = "创建数据的用户ID，系统根据token自动填写", required = true)
    private UserBaseDTO createUser;

    @ApiModelProperty(position = 910, value = "创建数据的时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date createdTime;

    @ApiModelProperty(position = 920, value = "最后修改者的ID，系统根据token自动填写", required = true)
    private UserBaseDTO lastModifyUser;

    @ApiModelProperty(position = 930, value = "最后的修收时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date lastModifiedTime;
}
