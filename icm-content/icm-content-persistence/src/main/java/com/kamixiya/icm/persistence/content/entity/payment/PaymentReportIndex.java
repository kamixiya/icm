package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 事前资金申请——指标信息
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_index", indexes = {
        @Index(name = "udx_icm_payment_report_index", columnList = "payment_report_id,expense_type,index_id", unique = true)
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportIndex extends AbstractBaseEntity {
    /**
     * 事前资金申请
     */
    @ManyToOne
    @JoinColumn(name = "payment_report_id", nullable = false)
    private PaymentReport paymentReport;

    /**
     * 费用类型，多种费用类型要分开
     */
    @Column(name = "expense_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    /**
     * 指标
     */
    @ManyToOne
    @JoinColumn(name = "index_id", nullable = false)
    private IndexLibrary index;

    /**
     * 指标余额
     */
    @Column(name = "available_amount", nullable = false)
    private Double availableAmount;

    /**
     * 申请金额（元）
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;

    /**
     * 在途金额
     */
    @Column(name = "passage_amount")
    private Double passageAmount = 0.0;

    /**
     * 释放金额
     */
    @Column(name = "release_amount")
    private Double releaseAmount = 0.0;

}
