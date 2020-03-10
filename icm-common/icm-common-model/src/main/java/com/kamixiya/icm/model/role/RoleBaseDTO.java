package com.kamixiya.icm.model.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 角色基本信息
 *
 * @author Zhu Jie
 * @date 2020/3/11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class RoleBaseDTO implements Serializable {


    @ApiModelProperty(position = 1, notes = "对象ID，新增时应当为null, 系统会自动生成", required = true)
    private String id;

    @ApiModelProperty(position = 100, value = "名称, 要求唯一性", required = true)
    @NotNull
    @Size(max = 50)
    private String name;

    @ApiModelProperty(position = 110, value = "备注，说明该Role应当分配给什么样的人使用", required = true, allowEmptyValue = true)
    @Size(max = 500)
    private String remark;
}
