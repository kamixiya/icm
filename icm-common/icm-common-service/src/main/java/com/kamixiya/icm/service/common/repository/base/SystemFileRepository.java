package com.kamixiya.icm.service.common.repository.base;

import com.kamixiya.icm.service.common.entity.base.SystemFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * SystemFileRepository
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@Repository
public interface SystemFileRepository extends JpaRepository<SystemFile, Long>, JpaSpecificationExecutor<SystemFile> {
}
