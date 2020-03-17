package com.kamixiya.icm.service.common.repository.organization;

import com.kamixiya.icm.service.common.entity.organization.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * DepartmentRepository
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
}

