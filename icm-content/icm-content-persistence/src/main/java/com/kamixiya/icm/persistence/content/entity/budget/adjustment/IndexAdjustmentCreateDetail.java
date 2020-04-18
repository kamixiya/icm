package com.kamixiya.icm.persistence.content.entity.budget.adjustment;

import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * IndexAdjustmentCreateDetail
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Entity
@Table(name = "zj_icm_index_adjustment_create_detail")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class IndexAdjustmentCreateDetail extends AbstractBaseEntity {
    /**
     * 指标（预算）调整
     */
    @ManyToOne
    @JoinColumn(name = "adjustment_id", nullable = false)
    private IndexAdjustment adjustment;

    /**
     * 项目名称
     */
    @Column(name = "project_name", nullable = false, length = 200)
    private String projectName;

    /**
     * 指标总额
     */
    @Column(name = "index_amount", nullable = false)
    private Double indexAmount;

    /**
     * 所属部门
     */
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Organization department;

    /**
     * 指标
     */
    @ManyToOne
    @JoinColumn(name = "index_id")
    private IndexLibrary index;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
