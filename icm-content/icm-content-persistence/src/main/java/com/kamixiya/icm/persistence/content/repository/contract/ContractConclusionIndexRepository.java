package com.kamixiya.icm.persistence.content.repository.contract;

import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusionIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;

/**
 * 合同订立--指标信息 Repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface ContractConclusionIndexRepository extends JpaRepository<ContractConclusionIndex, Long>, JpaSpecificationExecutor<ContractConclusionIndex> {
    /**
     * 更新合同备案的金额
     *
     * @param paidAmount    已支付金额变化前后的差值  增加为正，减少为负
     * @param passageAmount 在途金额变化前后的差值
     * @param ids           id数组，数组中的id变化金额必须一致，否则只传单个id
     */
    @Modifying(flushAutomatically = true)
    @Query(value = "update ContractConclusionIndex set paidAmount = paidAmount + :paidAmount, passageAmount = passageAmount + :passageAmount where id in (:ids)")
    void updateAmount(@Param("paidAmount") Double paidAmount, @Param("passageAmount") Double passageAmount, @NotEmpty @Param("ids") Long... ids);
}
