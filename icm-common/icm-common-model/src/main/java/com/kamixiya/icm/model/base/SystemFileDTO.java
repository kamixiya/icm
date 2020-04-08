package com.kamixiya.icm.model.base;

import com.kamixiya.icm.model.security.user.UserBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 系统文件管理使用的数据类
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@ApiModel(description = "系统文件类")
@Data
public class SystemFileDTO {

    @ApiModelProperty(position = 1, notes = "ID", required = true)
    private String id;

    @ApiModelProperty(value = "现文件名", required = true)
    private String name;

    @ApiModelProperty(value = "原文件名", required = true)
    private String originalName;

    @ApiModelProperty(value = "文件大小", required = true)
    private Long size;

    @ApiModelProperty(value = "文件分类，由使用者自行定义", required = true)
    private String type;

    @ApiModelProperty(position = 100, value = "创建数据的用户ID，系统根据token自动填写", required = true)
    private UserBaseDTO createUser;

    @ApiModelProperty(position = 110, value = "创建数据的时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date createdTime;

    @ApiModelProperty(position = 120, value = "最后修改者的ID，系统根据token自动填写", required = true)
    private UserBaseDTO lastModifyUser;

    @ApiModelProperty(position = 130, value = "最后的修收时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date lastModifiedTime;

    @ApiModelProperty(position = 131, value = "引用id")
    private String referenceId;


}