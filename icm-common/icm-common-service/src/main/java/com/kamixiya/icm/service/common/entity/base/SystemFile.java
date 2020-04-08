package com.kamixiya.icm.service.common.entity.base;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统文件，用于存储各种上传的文件资源，文件内容并不保存在数据，只保存文件的路径
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@Entity
@Table(name = "zj_sys_file")
@Getter
@Setter
public class SystemFile extends AbstractBaseEntity {

    /**
     * 保存在文件存储的文件名
     */
    @Column(name = "name", length = 200, nullable = false, unique = true)
    private String name;

    /**
     * 原始文件名
     */
    @Column(name = "original_name", length = 200, nullable = false)
    private String originalName;

    /**
     * 文件的长度
     */
    @Column(name = "file_size", nullable = false)
    private Long size;

    /**
     * 文件类型，可根据业务需要自行定义, 例如：项目，计划，底稿等
     */
    @Column(name = "file_type", length = 50, nullable = false)
    private String type;

    /**
     * 引用id
     */
    @Column(name = "reference_id", length = 50)
    private String referenceId;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
