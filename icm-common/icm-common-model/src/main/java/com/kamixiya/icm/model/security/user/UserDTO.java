package com.kamixiya.icm.model.security.user;

import com.kamixiya.icm.model.organization.department.DepartmentBaseDTO;
import com.kamixiya.icm.model.organization.employee.EmployeeBaseDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.position.PositionBaseDTO;
import com.kamixiya.icm.model.organization.unit.UnitBaseDTO;
import com.kamixiya.icm.model.security.authority.AuthorityBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Set;

/**
 * 用户信息
 *
 * @author Zhu Jie
 * @date 2020/3/8
 */
@ApiModel(description = "用户信息")
@Getter
@Setter
@ToString(callSuper = true)
public class UserDTO extends UserBaseDTO{

    @ApiModelProperty(position = 300, value = "最后修改密码的时间，系统自动生成", required = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @Null
    private Date changePasswordTime;

    @ApiModelProperty(position = 310, value = "账号是否被锁住（通常是连续输错密码）")
    @Null
    private Boolean accountLocked;

    @ApiModelProperty(position = 320, value = "权限（所属角色和所属组织的权限）")
    private Set<String> authorities;

    @ApiModelProperty(position = 400, value = "用户所属员工", required = true, allowEmptyValue = true)
    private EmployeeBaseDTO assignedEmployee;

    @ApiModelProperty(position = 430, value = "拥有该用户的所有权限")
    private Set<AuthorityBaseDTO> assignedAuthority;

    @ApiModelProperty(position = 431, value = "当前用户所属最顶级organization")
    private Set<OrganizationDTO> topOrganizationDTOS;

    @ApiModelProperty(position = 432, value = "当前用户所处的工作单位")
    private UnitBaseDTO unitDTO;

    @ApiModelProperty(position = 433, value = "当前用户所处的工作部门")
    private DepartmentBaseDTO departmentDTO;

    @ApiModelProperty(position = 434, value = "当前用户所处的工作岗位")
    private PositionBaseDTO positionDTO;

    @ApiModelProperty(position = 435, value = "当前用户所属最末级organization")
    private Set<OrganizationDTO> endOrganizationDTOS;

}
