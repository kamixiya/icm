package com.kamixiya.icm.model.organization.employee;

import com.kamixiya.icm.model.organization.position.PositionDTO;
import com.kamixiya.icm.model.security.user.UserBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * EmployeeDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "员工信息")
@Getter
@Setter
@ToString(callSuper = true)
public class EmployeeDTO extends EmployeeBaseDTO {

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

    @ApiModelProperty(position = 360, value = "员工所属用户", required = true, allowEmptyValue = true)
    private UserBaseDTO assignedUser;

    @ApiModelProperty(position = 37, value = "所属岗位(带父组织)", required = true)
    private List<PositionDTO> assignedPositions;

}
