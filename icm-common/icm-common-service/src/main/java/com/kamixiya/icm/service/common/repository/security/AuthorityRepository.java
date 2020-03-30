package com.kamixiya.icm.service.common.repository.security;

import com.kamixiya.icm.service.common.entity.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 权限的数据库操作
 *
 * @author Zhu Jie
 * @date 2020/3/30
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {
    /**
     * 根据ID数组查询权限集合
     *
     * @param ids ID数组
     * @return 权限集合
     */
    @Query("select r from Authority as r where r.id in (:ids)")
    Set<Authority> findAuthorities(@Param("ids") long[] ids);

    /**
     * 类型分组查询(有条件)
     *
     * @param type 类型
     * @return 分组后的类型集合
     */
    @Query("select r.type from Authority as r where r.type = :type group by r.type ORDER BY  r.type ")
    Set<String> getAuthorityTypes(String type);

    /**
     * 类型分组查询(无条件)
     *
     * @return 分组后的类型集合
     */
    @Query("select r.type from Authority as r group by r.type ORDER BY  r.type ")
    Set<String> getAuthorityTypes();


    /**
     * 通过权限编码查询权限ID
     *
     * @param code 权限编码
     * @return 权限ID
     */
    @Query("select a.id from Authority as a where a.code = :code")
    Long findAuthoritiesByCode(@Param("code") String code);
}