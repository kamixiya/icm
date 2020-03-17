package com.kamixiya.icm.model.organization.position;

import com.kamixiya.icm.model.organization.department.DepartmentBaseDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.unit.UnitBaseDTO;
import com.kamixiya.icm.model.security.user.UserBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * PositionDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "岗位信息")
@Getter
@Setter
@ToString(callSuper = true)
public class PositionDTO extends PositionBaseDTO {

    @ApiModelProperty(position = 310, value = "创建数据的用户ID，系统根据token自动填写", required = true)
    private UserBaseDTO createUser;

    @ApiModelProperty(position = 320, value = "创建数据的时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date createdTime;

    @ApiModelProperty(position = 330, value = "最后修改者的ID，系统根据token自动填写", required = true)
    private UserBaseDTO lastModifyUser;

    @ApiModelProperty(position = 340, value = "最后的修收时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date lastModifiedTime;

    @ApiModelProperty(position = 350, value = "数据过滤路径，系统自动填写", required = true)
    private String filterPath;

    @ApiModelProperty(position = 360, value = "所属单位（不为null则是岗位直属单位）", required = true)
    private UnitBaseDTO assignedUnit;

    @ApiModelProperty(position = 370, value = "所属部门（不为null则是岗位直属部门）", required = true)
    private DepartmentBaseDTO assignedDepartment;

    @ApiModelProperty(position = 400, value = "上级岗位", required = true)
    private PositionBaseDTO parent;

    @ApiModelProperty(position = 1006, value = "岗位对应的组织", required = true)
    private OrganizationDTO assignedOrganization;


}