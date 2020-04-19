package com.kamixiya.icm.model.content.purchase;

import com.kamixiya.icm.model.base.SystemFileDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * AttachDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "附件详细信息")
@Getter
@Setter
@ToString
public class AttachDTO extends AttachBaseDTO {
    @ApiModelProperty(position = 100, value = "ID")
    private String id;

    @ApiModelProperty(position = 500, value = "附件")
    private List<SystemFileDTO> files;

    @ApiModelProperty(position = 710, value = "排序")
    private Integer showOrder;
}