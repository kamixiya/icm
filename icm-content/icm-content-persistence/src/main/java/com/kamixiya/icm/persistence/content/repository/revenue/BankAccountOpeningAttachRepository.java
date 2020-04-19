package com.kamixiya.icm.persistence.content.repository.revenue;

import com.kamixiya.icm.persistence.content.entity.revenue.opening.BankAccountOpeningAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 银行开户申请附件 Repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface BankAccountOpeningAttachRepository extends JpaRepository<BankAccountOpeningAttach, Long>, JpaSpecificationExecutor<BankAccountOpeningAttach> {
}
