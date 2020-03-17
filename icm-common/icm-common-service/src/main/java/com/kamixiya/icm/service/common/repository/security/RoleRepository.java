package com.kamixiya.icm.service.common.repository.security;

import com.kamixiya.icm.service.common.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * RoleRepository
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    /**
     * 根据ID数组查询角色集合
     *
     * @param ids ID数组
     * @return 角色集合
     */
    @Query("select r from Role as r where r.id in (:ids)")
    Set<Role> findRoles(@Param("ids") long[] ids);
}
