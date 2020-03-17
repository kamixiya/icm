package com.kamixiya.icm.model.organization.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * EmployeeEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "创建或者修改员工信息")
@Getter
@Setter
@ToString(callSuper = true)
public class EmployeeEditInfoDTO extends EmployeeBaseDTO {

    @ApiModelProperty(position = 400, value = "所属岗位", required = true)
    @NotEmpty
    private List<String> positionIds;

    @ApiModelProperty(position = 410, value = "关联用户", allowEmptyValue = true)
    private String userId;
}
