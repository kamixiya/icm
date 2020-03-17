package com.kamixiya.icm.service.common.repository.organization;

import com.kamixiya.icm.service.common.entity.organization.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * UnitRepository
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {

    /**
     * 通过单位编码查询单位
     *
     * @param unitNumber 参数名
     * @return 参数ID
     */
    @Query("select u.id from Unit as u where u.unitNumber = :unitNumber")
    Long findUnitByUnitNumber(@Param("unitNumber") String unitNumber);
}
