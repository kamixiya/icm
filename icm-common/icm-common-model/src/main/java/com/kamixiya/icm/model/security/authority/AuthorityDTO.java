package com.kamixiya.icm.model.security.authority;

import com.kamixiya.icm.model.security.role.RoleBaseDTO;
import com.kamixiya.icm.model.security.user.UserBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

/**
 * AuthorityDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Getter
@Setter
@ToString
public class AuthorityDTO extends AuthorityBaseDTO {

    @ApiModelProperty(position = 310, value = "创建数据的用户", required = true)
    private UserBaseDTO createUser;

    @ApiModelProperty(position = 320, value = "创建数据的时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date createdTime;

    @ApiModelProperty(position = 330, value = "最后修改者，系统根据token自动填写", required = true)
    private UserBaseDTO lastModifyUser;

    @ApiModelProperty(position = 340, value = "最后的修收时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date lastModifiedTime;

    @ApiModelProperty(position = 350, value = "数据过滤路径，系统自动填写", required = true)
    private String filterPath;

    @ApiModelProperty(position = 360, value = "拥有该权限的所有角色，null时表示数据没有传输")
    private Set<RoleBaseDTO> assignedRoles;

}
