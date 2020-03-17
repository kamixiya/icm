package com.kamixiya.icm.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 调用分页接口成功时返回的数据类
 *
 * @param <T> Collection中所存放的数据类型
 * @author Zhu Jie
 * @date 2020/3/11
 */
@ApiModel(description = "通用分页数据, 根据调用接口返回不同的数据类型")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDataDTO<T> {

    @ApiModelProperty(position = 1, value = "当前页码，从0开始计数", required = true)
    private Integer pageNo;

    @ApiModelProperty(position = 2, value = "每页纪录条数", required = true)
    private Integer pageSize;

    @ApiModelProperty(position = 3, value = "总纪录条数", required = true)
    private Long totalRecords;

    @ApiModelProperty(position = 4, value = "总页数", required = true)
    private Integer totalPages;

    @ApiModelProperty(position = 5, value = "数据", required = true)
    private Collection<T> data;
}
