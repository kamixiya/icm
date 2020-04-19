package com.kamixiya.icm.persistence.content.repository.payment;

import com.kamixiya.icm.persistence.content.entity.payment.PaymentReportContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 事前资金申请----合同付款信息 Repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface PaymentReportContractRepository extends JpaRepository<PaymentReportContract, Long>, JpaSpecificationExecutor<PaymentReportContract> {
}
