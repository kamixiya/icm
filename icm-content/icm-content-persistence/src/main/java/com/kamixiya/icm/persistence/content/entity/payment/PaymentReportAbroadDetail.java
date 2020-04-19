package com.kamixiya.icm.persistence.content.entity.payment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 因公出国支出详情
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_abroad_detail")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportAbroadDetail extends PaymentReportDetailBaseEntity {
    /**
     * 因公出国
     */
    @ManyToOne
    @JoinColumn(name = "abroad_expense_id", nullable = false)
    private PaymentReportAbroadExpense abroadExpense;

    /**
     * 在途金额
     */
    @Column(name = "passage_amount")
    private Double passageAmount = 0.0;

    /**
     * 外币
     */
    @Column(name = "foreign_amount")
    private Double foreignAmount = 0.0;

    /**
     * 汇率
     */
    @Column(name = "exchange_rate")
    private Double exchangeRate = 0.0;
}
