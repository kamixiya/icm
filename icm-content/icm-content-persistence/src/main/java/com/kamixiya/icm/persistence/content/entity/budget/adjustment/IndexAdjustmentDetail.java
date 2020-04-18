package com.kamixiya.icm.persistence.content.entity.budget.adjustment;

import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Entity
@Table(name = "zj_icm_index_adjustment_detail")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class IndexAdjustmentDetail extends AbstractBaseEntity {
    /**
     * 指标（预算）调整
     */
    @ManyToOne
    @JoinColumn(name = "adjustment_id", nullable = false)
    private IndexAdjustment adjustment;

    @Column(name = "detail_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdjustDetailType detailType;

    /**
     * 指标
     */
    @ManyToOne
    @JoinColumn(name = "index_id", nullable = false)
    private IndexLibrary indexLibrary;

    /**
     * 指标总额
     */
    @Column(name = "index_amount", nullable = false)
    private Double indexAmount;

    /**
     * 指标余额
     */
    @Column(name = "available_amount", nullable = false)
    private Double availableAmount;

    /**
     * 占用金额
     */
    @Column(name = "occupation_amount", nullable = false)
    private Double occupationAmount;

    /**
     * 调增金额
     */
    @Column(name = "adjust_amount", nullable = false)
    private Double adjustAmount;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}

