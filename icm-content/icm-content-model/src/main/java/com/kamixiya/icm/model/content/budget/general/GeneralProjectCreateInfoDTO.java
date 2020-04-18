package com.kamixiya.icm.model.content.budget.general;

import com.kamixiya.icm.model.content.budget.general.attach.GeneralProjectAttachEditInfoDTO;
import com.kamixiya.icm.model.content.budget.general.detail.GeneralProjectDetailEditInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "项目库创建信息")
@Getter
@Setter
@ToString
public class GeneralProjectCreateInfoDTO extends GeneralProjectBaseDTO {
    @ApiModelProperty(position = 500, value = "申报单位ID", required = true)
    @NotBlank
    private String unitId;

    @ApiModelProperty(position = 510, value = "申报部门ID", required = true)
    @NotBlank
    private String departmentId;

    @ApiModelProperty(position = 520, value = "主管部门ID", required = true)
    @NotBlank
    private String administrativeDepartmentId;

    @ApiModelProperty(position = 535, value = "项目明细及说明")
    private List<GeneralProjectDetailEditInfoDTO> projectDetails;

    @ApiModelProperty(position = 550, value = "附件信息")
    private List<GeneralProjectAttachEditInfoDTO> projectAttaches;

}
