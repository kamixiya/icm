package com.kamixiya.icm.model.content.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 分页查询参数基础封装类
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@Getter
@Setter
@AllArgsConstructor
public class PageQueryOptionDTO {

    @ApiModelProperty(position = 100, value = "页号，从0开始", required = true)
    @Min(0)
    private int page;

    @ApiModelProperty(position = 110, value = "每页纪录条数", required = true)
    @Min(1)
    @Max(100)
    private int size;

    @ApiModelProperty(position = 120, value = "排序字段, 例如：字段1,asc,字段2,desc")
    private String sort;
}
