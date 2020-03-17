package com.kamixiya.icm.model.organization.unit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * UnitEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "创建或者修改单位信息")
@Getter
@Setter
@ToString(callSuper = true)
public class UnitEditInfoDTO extends UnitBaseDTO {

    @ApiModelProperty(position = 420, value = "上级单位", required = true)
    private String parentId;

    @ApiModelProperty(position = 430, value = "地区id")
    private String districtId;
}
