package com.kamixiya.icm.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用简单数据类型,类型根据业务而定, 通常用于包裹Boolean, Integer, Float等简单的数据类型，方便前端
 * 使用JSON来解析，不可用于POJO。
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@ApiModel(description = "通用简单数据类型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequstDataDTO<T> {

    @ApiModelProperty(value = "人员", required = true, allowEmptyValue = true)
    T employeeIds;

    @ApiModelProperty(value = "组织结构id数组", required = true, allowEmptyValue = true)
    T organizationIds;

}

