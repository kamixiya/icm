package com.kamixiya.icm.model.organization.position;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * PositionBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class PositionBaseDTO implements Serializable {

    @ApiModelProperty(position = 1, value = "对象ID，新增时应当为null, 系统会自动生成", required = true)
    private String id;

    @ApiModelProperty(position = 100, value = "名称", required = true)
    @NotNull
    @Size(max = 100)
    private String name;

    @ApiModelProperty(position = 110, value = "简称", required = true)
    @Size(max = 100)
    private String shortName;

    @ApiModelProperty(position = 120, value = "在OA系统中的ID")
    @Size(max = 40)
    private String oaId;

    @ApiModelProperty(position = 130, value = "是否可用", required = true)
    @NotNull
    private Boolean available;

    @ApiModelProperty(position = 141, value = "是否内部", required = true)
    @NotNull
    private Boolean interior;

    @ApiModelProperty(position = 140, value = "显示顺序", required = true)
    private Integer showOrder;

}