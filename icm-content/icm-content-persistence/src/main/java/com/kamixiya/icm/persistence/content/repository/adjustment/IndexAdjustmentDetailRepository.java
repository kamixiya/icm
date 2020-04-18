package com.kamixiya.icm.persistence.content.repository.adjustment;

import com.kamixiya.icm.persistence.content.entity.budget.adjustment.IndexAdjustmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * IndexAdjustmentDetailRepository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface IndexAdjustmentDetailRepository extends JpaRepository<IndexAdjustmentDetail, Long>, JpaSpecificationExecutor<IndexAdjustmentDetail> {
}