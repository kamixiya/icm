package com.kamixiya.icm.persistence.content.repository.purchase;

import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 采购内容
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface PurchaseReportDetailRepository extends JpaRepository<PurchaseReportDetail, Long>, JpaSpecificationExecutor<PurchaseReportDetail> {
}
