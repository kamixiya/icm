package com.kamixiya.icm.persistence.content.entity.payment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 培训费支出详情
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_training_detail")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportTrainingDetail extends PaymentReportDetailBaseEntity {
    /**
     * 培训费
     */
    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private PaymentReportTraining training;

    /**
     * 已报销金额
     */
    @Column(name = "paid_amount", nullable = false)
    private Double paidAmount = 0.00;

    /**
     * 在途金额
     */
    @Column(name = "passage_amount")
    private Double passageAmount = 0.0;
}
