package com.kamixiya.icm.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 前端将有序的对象ID集合返回，对象更换父类后，对象集合按照有序的对象ID集合排序
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@ApiModel(description = "前端将有序的对象ID集合返回，对象更换父类后，对象集合按照有序的对象ID集合排序")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectChangeParentDataDTO {

    @ApiModelProperty(value = "父对象ID", required = true, allowEmptyValue = true)
    private String parentId;

    @ApiModelProperty(value = "有序对象ID集合", required = true, allowEmptyValue = true)
    private List<String> ids;
}
