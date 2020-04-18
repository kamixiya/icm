package com.kamixiya.icm.persistence.content.repository.budget;

import com.kamixiya.icm.persistence.content.entity.budget.general.GeneralProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * GeneralProjectDetailRepository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface GeneralProjectDetailRepository extends JpaRepository<GeneralProjectDetail, Long>, JpaSpecificationExecutor<GeneralProjectDetail> {
}