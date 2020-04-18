package com.kamixiya.icm.persistence.content.repository.adjustment;

import com.kamixiya.icm.persistence.content.entity.budget.adjustment.IndexAdjustmentCreateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * IndexAdjustmentCreateDetailRepository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface IndexAdjustmentCreateDetailRepository extends JpaRepository<IndexAdjustmentCreateDetail, Long>, JpaSpecificationExecutor<IndexAdjustmentCreateDetail> {
}

