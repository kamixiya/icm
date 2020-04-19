package com.kamixiya.icm.persistence.content.repository.payment;

import com.kamixiya.icm.persistence.content.entity.payment.PaymentReportAbroadExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 事前资金申请----因公出国(境)经费 Repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface PaymentReportAbroadExpenseRepository extends JpaRepository<PaymentReportAbroadExpense, Long>, JpaSpecificationExecutor<PaymentReportAbroadExpense> {
}
