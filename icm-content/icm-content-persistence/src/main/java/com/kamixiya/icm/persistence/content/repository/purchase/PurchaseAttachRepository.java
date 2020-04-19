package com.kamixiya.icm.persistence.content.repository.purchase;

import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 采购计划附件详细
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface PurchaseAttachRepository extends JpaRepository<PurchaseAttach, Long>, JpaSpecificationExecutor<PurchaseAttach> {
}
