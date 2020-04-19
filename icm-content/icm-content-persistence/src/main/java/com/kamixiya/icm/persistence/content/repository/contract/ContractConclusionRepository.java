package com.kamixiya.icm.persistence.content.repository.contract;

import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 合同订立 Repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface ContractConclusionRepository extends JpaRepository<ContractConclusion, Long>, JpaSpecificationExecutor<ContractConclusion> {

    /**
     * 根据合同编号查询
     *
     * @param contractNo 合同编号
     * @return 合同订立
     */
    ContractConclusion findByContractNo(String contractNo);
}
