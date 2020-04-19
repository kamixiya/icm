package com.kamixiya.icm.persistence.content.repository.contract;

import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusionAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 合同订立附件 Repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface ContractConclusionAttachRepository extends JpaRepository<ContractConclusionAttach, Long>, JpaSpecificationExecutor<ContractConclusionAttach> {
}
