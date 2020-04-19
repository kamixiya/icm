package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 事前资金申请——接待费用实体
 *
 * @author maqionggui
 * @date 2019/12/3
 */
@Entity
@Table(name = "zj_icm_payment_report_guest_expense")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportGuestExpense extends AbstractBaseEntity {
    /**
     * 事前资金申请
     */
    @ManyToOne
    @JoinColumn(name = "payment_report_id", nullable = false)
    private PaymentReport paymentReport;

    /**
     * 费用类型
     */
    @Column(name = "expense_type", nullable = false)
    private String expenseTypeDetail;

    /**
     * 金额
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

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
