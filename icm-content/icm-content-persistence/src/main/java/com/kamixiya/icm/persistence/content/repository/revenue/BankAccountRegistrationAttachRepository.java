package com.kamixiya.icm.persistence.content.repository.revenue;

import com.kamixiya.icm.persistence.content.entity.revenue.Registration.BankAccountRegistrationAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 银行账户登记附件 Repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface BankAccountRegistrationAttachRepository extends JpaRepository<BankAccountRegistrationAttach, Long>, JpaSpecificationExecutor<BankAccountRegistrationAttach> {
}
