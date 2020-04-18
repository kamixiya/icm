package com.kamixiya.icm.persistence.content.entity.budget.general;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 项目库附件信息
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Entity
@Table(name = "zj_icm_project_general_attach", indexes = {
        @Index(name = "idx_project_general_attach", columnList = "name")
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class GeneralProjectAttach extends AbstractBaseEntity {
    /**
     * 项目库
     */
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private GeneralProject project;

    /**
     * 申报材料类型
     */
    @Column(name = "material_type", nullable = false)
    private String materialType;

    /**
     * 文号
     */
    @Column(name = "doc_num", length = 100)
    private String docNum;

    /**
     * 附件名称
     */
    @Column(name = "name", length = 200)
    private String name;

    /**
     * 上传时间
     */
    @Column(name = "upload_date")
    private Date uploadDate;

    /**
     * 附件
     */
    @OneToOne(targetEntity = SystemFile.class)
    @JoinColumn(name = "file_id")
    private SystemFile file;

    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}