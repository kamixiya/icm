package com.kamixiya.icm.persistence.content.entity.budget.adjustment;

import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Entity
@Table(name = "zj_icm_index_adjustment", indexes = {
        @Index(name = "idx_icm_index_adjustment", columnList = "code")
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class IndexAdjustment extends AbstractBaseEntity {

    /**
     * 审批状态
     */
    @Column(name = "state_type")
    @Enumerated(EnumType.STRING)
    private StateType stateType;

    /**
     * 调剂编号
     */
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    /**
     * 调剂时间
     */
    @Column(name = "adjust_date", nullable = false)
    private Date adjustDate;

    /**
     * 项目编号
     */
    @Column(name = "project_code", nullable = false)
    private String projectCode;

    /**
     * 项目名称
     */
    @Column(name = "project_name", nullable = false, length = 200)
    private String projectName;

    /**
     * 调剂类型
     */
    @Column(name = "adjust_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdjustType adjustType;

    /**
     * 调剂依据
     */
    @Column(name = "basis", length = 500)
    private String basis;

    /**
     * 调剂总额
     */
    @Column(name = "total", nullable = false)
    private Double total;

    /**
     * 申报单位
     */
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Organization unit;

    /**
     * 申报部门
     */
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Organization department;

    /**
     * 附件
     */
    @ManyToMany
    @JoinTable(
            name = "zj_icm_index_adjustment__file",
            joinColumns = @JoinColumn(name = "adjustment_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<SystemFile> files = new ArrayList<>();

    /**
     * 指标信息
     */
    @OneToMany(mappedBy = "adjustment")
    @OrderBy("detail_type asc , show_order ASC")
    private List<IndexAdjustmentDetail> adjustmentDetails = new ArrayList<>();

    /**
     * 指标新增信息，新增指标时不能为空
     */
    @OneToMany(mappedBy = "adjustment")
    @OrderBy("show_order ASC")
    private List<IndexAdjustmentCreateDetail> createDetails = new ArrayList<>();

}