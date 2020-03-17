package com.kamixiya.icm.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 简单的树结点数据
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "简单的树结点数据")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class SimpleTreeDataDTO implements Serializable, Cloneable {

    @ApiModelProperty(position = 1, notes = "ID", required = true, allowEmptyValue = true)
    private String id;

    @ApiModelProperty(position = 110, value = "名称", required = true)
    private String name;

    @ApiModelProperty(position = 120, value = "是否为叶子节点", required = true)
    private Boolean isLeaf;

    @ApiModelProperty(position = 130, value = "级次，标识该结点在树结构中的层次，ROOT结点为1", required = true)
    private Integer treeLevel;

    @ApiModelProperty(position = 131, value = "树结点路径，格式：父结点ID-自己的ID-", required = true)
    private String treePath;

    @ApiModelProperty(position = 132, value = "显示顺序", required = true)
    private Integer showOrder;

    @ApiModelProperty(position = 140, value = "类型，业务模块应根据业务需求自行定义")
    private String type;

    @ApiModelProperty(position = 150, notes = "子节点列表，应根据使用情景决定该属性是否可用")
    private List<SimpleTreeDataDTO> children;

    @ApiModelProperty(position = 160, notes = "父节点，应根据使用情景决定该属性是否可用")
    private SimpleTreeDataDTO parent;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
