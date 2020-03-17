package com.kamixiya.icm.model.organization.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DepartmentEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "创建或者修改部门信息")
@Getter
@Setter
@ToString(callSuper = true)
public class DepartmentEditInfoDTO extends DepartmentBaseDTO {

    @ApiModelProperty(position = 400, value = "所属单位，如果部门直属单位，需填写，否则为null", required = true)
    private String unitId;

    @ApiModelProperty(position = 400, value = "所属上级部门，没有则为null", required = true)
    private String parentId;
}
