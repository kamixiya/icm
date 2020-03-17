package com.kamixiya.icm.model.organization.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * OrganizationBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "组织基本信息")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class OrganizationBaseDTO implements Serializable {
    @ApiModelProperty(position = 1, notes = "对象ID，新增时应当为null, 系统会自动生成", required = true)
    private String id;

    @ApiModelProperty(position = 110, value = "名称", required = true)
    private String name;

    @ApiModelProperty(position = 120, value = "简称", required = true, allowEmptyValue = true)
    private String shortName;

    @ApiModelProperty(position = 130, value = "组织架构类型, 标识关联是单位，部门，还是岗位", required = true)
    private OrganizationType organizationType;

    @ApiModelProperty(position = 140, value = "是否可用，与关联的对象相同", required = true)
    private Boolean available;

    @ApiModelProperty(position = 141, value = "是否内部", required = true)
    private Boolean interior;

    @ApiModelProperty(position = 180, value = "显示顺序", required = true)
    @NotNull
    private Integer showOrder;

    @ApiModelProperty(position = 182, value = "是否可选")
    private Boolean select;

    @ApiModelProperty(position = 182, value = "子集是否可选")
    private Boolean childrenSelect;

    @ApiModelProperty(position = 390, notes = "父组织ID")
    private String parentId;
}

