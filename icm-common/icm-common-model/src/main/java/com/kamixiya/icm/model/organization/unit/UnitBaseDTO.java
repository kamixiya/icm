package com.kamixiya.icm.model.organization.unit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 单位基本信息数据
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "单位基本信息数据")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class UnitBaseDTO implements Serializable {

    @ApiModelProperty(position = 1, value = "对象ID，新增时应当为null, 系统会自动生成", required = true)
    private String id;

    @ApiModelProperty(position = 2, value = "纳税人识别号")
    @Size(max = 100)
    private String taxpayerIdentificationNumber;

    @ApiModelProperty(position = 3, value = "传真号码")
    @Pattern(regexp = "^(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$", message = "传真号码错误,如:区号-号码!")
    @Size(max = 100)
    private String faxNumber;

    @ApiModelProperty(position = 4, value = "邮政编码")
    @Size(max = 100)
    @Pattern(regexp = "^\\d{6}$", message = "邮政编码错误,如:6位数字!")
    private String postalCode;

    @ApiModelProperty(position = 5, value = "单位地址")
    @Size(max = 100)
    private String unitAddress;

    @ApiModelProperty(position = 6, value = "固定电话")
    @Pattern(regexp = "^(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$", message = "固定电话错误,如:区号-号码!")
    @Size(max = 100)
    private String telephone;

    @ApiModelProperty(position = 7, value = "备注")
    @Size(max = 2000)
    private String remark;

    @ApiModelProperty(position = 8, value = "开户银行")
    @Size(max = 100)
    private String depositBank;

    @ApiModelProperty(position = 100, value = "名称", required = true)
    @NotNull
    @Size(max = 100)
    private String name;

    @ApiModelProperty(position = 101, value = "单位编码(只能输下划线英文字母数字,且唯一,如：_yyyy_888)", required = true)
    @NotNull
    @Pattern(regexp = "\\w+", message = "单位编码(只能输下划线或英文字母或数字,如：_yyyy_888)")
    @Size(max = 40)
    private String unitNumber;

    @ApiModelProperty(position = 102, value = "单位性质，对应字典值UNIT_NATURE")
    @Size(max = 100)
    private String unitNature;

    @ApiModelProperty(position = 110, value = "简称", required = true, allowEmptyValue = true)
    @Size(max = 100)
    private String shortName;

    @ApiModelProperty(position = 120, value = "在OA系统中的ID")
    @Size(max = 40)
    private String oaId;

    @ApiModelProperty(position = 130, value = "是否可用", required = true)
    @NotNull
    private Boolean available;

    @ApiModelProperty(position = 140, value = "显示顺序", required = true)
    private Integer showOrder;

    @ApiModelProperty(position = 141, value = "是否内部", required = true)
    @NotNull
    private Boolean interior;

}
