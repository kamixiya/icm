package com.kamixiya.icm.persistence.content.repository.purchase;

import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReportDetailIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 采购申请——指标详细
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface PurchaseReportDetailIndexRepository extends JpaRepository<PurchaseReportDetailIndex, Long>, JpaSpecificationExecutor<PurchaseReportDetailIndex> {
}
