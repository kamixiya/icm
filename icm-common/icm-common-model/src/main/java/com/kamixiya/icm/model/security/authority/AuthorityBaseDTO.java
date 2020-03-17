package com.kamixiya.icm.model.security.authority;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * AuthorityBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class AuthorityBaseDTO implements Serializable {

    @ApiModelProperty(position = 1, notes = "对象ID，新增时应当为null, 系统会自动生成", required = true)
    private String id;

    @ApiModelProperty(position = 100, value = "权限编号, 要求唯一性。需要使用\"数据类型_操作类型\"的形式, 操作类型为CRUD, 建议使用CREATE, RETRIEVE, UPDATE, DELETE", required = true)
    @NotNull
    @Size(min = 1, max = 50)
    private String code;

    @ApiModelProperty(position = 110, value = "权限名称", required = true)
    @NotNull
    @Size(max = 100)
    private String name;

    @ApiModelProperty(position = 120, value = "备注，说明该权限有何作用", required = true, allowEmptyValue = true)
    @Size(max = 500)
    private String remark;

    @ApiModelProperty(position = 130, value = "权限所属的类型", required = true)
    @Size(max = 50)
    private String type;


}
