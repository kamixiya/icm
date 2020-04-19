package com.kamixiya.icm.persistence.content.repository.payment;

import com.kamixiya.icm.persistence.content.entity.payment.PaymentReportGuestExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * 事前资金申请----接待费用 Repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface PaymentReportGuestExpenseRepository extends JpaRepository<PaymentReportGuestExpense, Long>, JpaSpecificationExecutor<PaymentReportGuestExpense> {

}
