package com.kamixiya.icm.persistence.content.repository.payment;

import com.kamixiya.icm.persistence.content.entity.payment.PaymentReportIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * 事前资金申请----指标信息 Repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface PaymentReportIndexRepository extends JpaRepository<PaymentReportIndex, Long>, JpaSpecificationExecutor<PaymentReportIndex> {
}
