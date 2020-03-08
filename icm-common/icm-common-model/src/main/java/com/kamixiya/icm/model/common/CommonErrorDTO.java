package com.kamixiya.icm.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 错误信息
 *
 * @author Zhu Jie
 * @date 2020/3/8
 */
@ApiModel(description = "通用API出错时，返回的错误信息数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonErrorDTO {

    @ApiModelProperty(position = 1, value = "错误信息", required = true)
    private String message;

    @ApiModelProperty(position = 2, value = "异常堆栈信息")
    private String exception;

}
