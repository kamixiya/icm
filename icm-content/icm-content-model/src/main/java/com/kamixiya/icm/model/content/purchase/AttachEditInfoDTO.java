package com.kamixiya.icm.model.content.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * AttachEditInfoDTO
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@ApiModel(description = "附件编辑信息")
@Getter
@Setter
@ToString
public class AttachEditInfoDTO extends AttachBaseDTO {
    @ApiModelProperty(position = 500, value = "附件ID列表")
    private List<String> fileIds;
}

