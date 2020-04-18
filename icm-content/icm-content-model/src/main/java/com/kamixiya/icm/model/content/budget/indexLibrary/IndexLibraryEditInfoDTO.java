package com.kamixiya.icm.model.content.budget.indexLibrary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "指标库详细信息")
@Getter
@Setter
@ToString
public class IndexLibraryEditInfoDTO extends IndexLibraryBaseDTO {

    @ApiModelProperty(position = 100, value = "ID，新建时无需输入")
    private String id;

    @ApiModelProperty(position = 130, value = "申报部门", required = true)
    private String departmentId;

}