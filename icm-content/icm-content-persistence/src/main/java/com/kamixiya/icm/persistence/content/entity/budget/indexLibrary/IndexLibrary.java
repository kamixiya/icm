package com.kamixiya.icm.persistence.content.entity.budget.indexLibrary;

import com.kamixiya.icm.persistence.content.entity.budget.adjustment.IndexAdjustmentDetail;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 指标库
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Entity
@Table(name = "zj_icm_index", indexes = {@Index(
        name = "idx_index",
        columnList = "year, project_name, department_id", unique = true
)})
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class IndexLibrary extends AbstractBaseEntity {

    /**
     * 指标年份
     */
    @Column(name = "year", nullable = false, length = 4)
    private String year;

    /**
     * 项目名称
     */
    @Column(name = "project_name", nullable = false, length = 200)
    private String projectName;

    /**
     * 项目编号
     */
    @Column(name = "project_code", length = 200)
    private String projectCode;

    /**
     * 大项目名称
     */
    @Column(name = "large_project_name", nullable = false, length = 200)
    private String largeProjectName;

    /**
     * 大项目编号
     */
    @Column(name = "large_project_code", nullable = false, length = 200)
    private String largeProjectCode;

    /**
     * 分配金额/批复金额,大于等于零
     */
    @Column(name = "allocation_amount", nullable = false)
    private Double allocationAmount = 0.0;

    /**
     * 占用金额,大于等于零
     */
    @Column(name = "occupation_amount", nullable = false)
    private Double occupationAmount = 0.0;

    /**
     * 可用金额（= 分配批复金额 + 调整金额 - 占用金额 - 在途金额）
     */
    @Column(name = "available_amount", nullable = false)
    private Double availableAmount = 0.0;

    /**
     * 调减在途金额
     */
    @Column(name = "reduce_passage_amount")
    private Double reducePassageAmount = 0.0;

    /**
     * 在途金额
     */
    @Column(name = "passage_amount", nullable = false)
    private Double passageAmount = 0.0;

    /**
     * 调整金额
     */
    @Column(name = "adjustment_amount", nullable = false)
    private Double adjustmentAmount = 0.0;

    /**
     * 申报部门
     */
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Organization department;

    /**
     * 申报单位
     */
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Organization unit;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;

    /**
     * 指标类型，默认值为批复指标
     */
    @Column(name = "index_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private IndexType indexType = IndexType.APPROVAL;


    @OneToMany(mappedBy = "indexLibrary")
    private Set<IndexAdjustmentDetail> adjustmentDetails = new LinkedHashSet<>();
}
