package com.kamixiya.icm.persistence.content.repository.purchase;

import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReportIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;

/**
 * 指标信息
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface PurchaseReportIndexRepository extends JpaRepository<PurchaseReportIndex, Long>, JpaSpecificationExecutor<PurchaseReportIndex> {

    /**
     * 更新采购指标的金额
     *
     * @param occupationAmount 占用金额变化前后的差值  增加为正，减少为负
     * @param passageAmount    在途金额变化前后的差值
     * @param ids              id数组，数组中的id变化金额必须一致，否则只传单个id
     */
    @Modifying(flushAutomatically = true)
    @Query(value = "update PurchaseReportIndex set occupationAmount = occupationAmount + :occupationAmount, " +
            "passageAmount = passageAmount + :passageAmount, availableAmount = amount - passageAmount - occupationAmount where id in (:ids)")
    void updateAmount(@Param("occupationAmount") Double occupationAmount, @Param("passageAmount") Double passageAmount, @NotEmpty @Param("ids") Long... ids);
}
