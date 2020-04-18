package com.kamixiya.icm.model.content.budget.general.attach;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "项目附件编辑信息")
@Getter
@Setter
@ToString
public class GeneralProjectAttachEditInfoDTO extends GeneralProjectAttachBaseDTO{
    @ApiModelProperty(position = 500, value = "附件ID")
    private String fileId;
}
