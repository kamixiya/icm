package com.kamixiya.icm.model.organization.organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * OrgTreeRequestDTO
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Getter
@Setter
@ToString(callSuper = true)
public class OrgTreeRequestDTO implements Serializable {


    @ApiModelProperty(position = 180, value = "延时加载选中的组织机构ID", notes = "延时加载选中的组织机构ID")
    private String selectedOrgId;

    @ApiModelProperty(position = 181, value = "默认是用户，employee指定为员工", notes = "默认是用户，employee指定为员工")
    private String type;


}

