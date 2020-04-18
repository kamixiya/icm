package com.kamixiya.icm.persistence.content.repository.budget;

import com.kamixiya.icm.persistence.content.entity.budget.general.GeneralProjectAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * GeneralProjectAttachRepository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface GeneralProjectAttachRepository extends JpaRepository<GeneralProjectAttach, Long>, JpaSpecificationExecutor<GeneralProjectAttach> {
}