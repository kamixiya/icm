package com.kamixiya.icm.model.organization.position;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * PositionEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "创建或者修改岗位信息")
@Getter
@Setter
@ToString(callSuper = true)
public class PositionEditInfoDTO extends PositionBaseDTO {

    @ApiModelProperty(position = 400, value = "所属单位，如果岗位直属单位，需填写，否则为null", required = true)
    private String unitId;

    @ApiModelProperty(position = 410, value = "所属部门，如果岗位直属部门，需填写，否则为null", required = true)
    private String departmentId;

    @ApiModelProperty(position = 420, value = "上级岗位", required = true)
    private String parentId;

}
