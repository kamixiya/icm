package com.kamixiya.icm.persistence.content.repository.budget;

import com.kamixiya.icm.persistence.content.entity.budget.general.GeneralProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 项目库repository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface GeneralProjectRepository extends JpaRepository<GeneralProject, Long>, JpaSpecificationExecutor<GeneralProject> {
    /**
     * 根据项目编号查找项目
     *
     * @param code 项目编号
     * @return 项目
     */
    GeneralProject findByCode(String code);
}
