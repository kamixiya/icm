package com.kamixiya.icm.service.common.repository.organization;

import com.kamixiya.icm.service.common.entity.organization.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PositionRepository
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, Long>, JpaSpecificationExecutor<Position> {
    /**
     * 根据岗位id集合 查询岗位信息
     *
     * @param posIds 岗位Id集合
     * @return 岗位集合
     */
    @Query(value = "select p from Position p where  p.id in (:posIds)")
    List<Position> findByPositionIds(@Param("posIds") List<Long> posIds);
}
