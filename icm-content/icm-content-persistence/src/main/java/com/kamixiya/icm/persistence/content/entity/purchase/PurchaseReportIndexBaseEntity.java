package com.kamixiya.icm.persistence.content.entity.purchase;

import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author Zhu Jie
 * @date 2020/4/19
 */
@MappedSuperclass
@Getter
@Setter
public class PurchaseReportIndexBaseEntity extends AbstractBaseEntity {
    @ManyToOne
    @JoinColumn(name = "index_id", nullable = false)
    private IndexLibrary index;

    /**
     * 指标余额
     */
    @Column(name = "available_amount", nullable = false)
    private Double availableAmount = 0.0;

    /**
     * 数量
     */
    @Column(name = "quantity", nullable = false)
    private Double quantity = 0.0;

    /**
     * 单价
     */
    @Column(name = "price")
    private Double price = 0.0;

    /**
     * 采购金额
     */
    @Column(name = "amount")
    private Double amount = 0.0;

    /**
     * 本年支出金额
     */
    @Column(name = "current_year_amount")
    private Double currentYearAmount = 0.0;

    /**
     * 占用金额
     */
    @Column(name = "occupation_amount")
    private Double occupationAmount = 0.0;

    /**
     * 在途金额
     */
    @Column(name = "passage_amount")
    private Double passageAmount = 0.0;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
