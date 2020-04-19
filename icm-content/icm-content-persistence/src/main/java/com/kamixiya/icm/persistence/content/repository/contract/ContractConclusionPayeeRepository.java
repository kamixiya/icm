package com.kamixiya.icm.persistence.content.repository.contract;

import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusionPayee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 收款人银行账户信息 Repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface ContractConclusionPayeeRepository extends JpaRepository<ContractConclusionPayee, Long>, JpaSpecificationExecutor<ContractConclusionPayee> {
}
