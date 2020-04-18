package com.kamixiya.icm.model.content.budget.general.attach;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * GeneralProjectAttachBaseDTO
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "项目附件基本信息")
@Getter
@Setter
@ToString
class GeneralProjectAttachBaseDTO {
    @ApiModelProperty(position = 110, value = "申报材料类型", required = true)
    @NotBlank
    private String materialType;

    @ApiModelProperty(position = 120, value = "文号")
    @Size(max = 100)
    private String docNum;

    @ApiModelProperty(position = 130, value = "附件名称")
    @Size(max = 200)
    private String name;

    @ApiModelProperty(position = 140, value = "上传时间")
    private Date uploadDate;
}

