package com.kamixiya.icm.model.content.budget.general.attach;

import com.kamixiya.icm.model.base.SystemFileDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "项目附件详细信息")
@Getter
@Setter
@ToString
public class GeneralProjectAttachDTO extends GeneralProjectAttachBaseDTO{
    @ApiModelProperty(position = 100, value = "ID，新建时无需输入")
    private String id;

    @ApiModelProperty(position = 700, value = "附件")
    private SystemFileDTO file;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}

