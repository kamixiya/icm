package com.kamixiya.icm.service.common.repository.organization;

import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * OrganizationRepository
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {

    /**
     * 根据filterPath查询组织
     *
     * @param filterPath 组织过滤路径
     * @return 组织对象
     */
    @Query(value = "select o from Organization o where o.filterPath = :filterPath")
    Organization findIdByFilterPath(@Param("filterPath") String filterPath);

    /**
     * 根据filterPath查询组织
     *
     * @param filterPath 组织过滤路径
     * @return 组织dto对象
     */
    @Query(value = "select o from Organization o " +
            "where o.filterPath like :filterPath order by o.treeLevel, o.showOrder")
    Set<Organization> findByFilterPath(@Param("filterPath") String filterPath);

    /**
     * 根据treePath,organizationType查询组织
     *
     * @param treePath         组织treePath
     * @param organizationType 组织类型
     * @return 组织dto对象
     */
    @Query(value = "select o from Organization o " +
            "where o.treePath like :treePath and o.organizationType = :organizationType order by o.treeLevel, o.showOrder")
    Set<Organization> findByTreePathAndOrganizationType(@Param("treePath") String treePath, @Param("organizationType") OrganizationType organizationType);

    /**
     * 根据id查询组织(类型为单位)的子单位
     *
     * @param id               组织id
     * @param organizationType 组织类型
     * @return 组织dto对象
     */
    @Query(value = "select o from Organization o " +
            "where o.parent = :id and o.organizationType = :organizationType")
    Set<Organization> findChildrenById(@Param("id") Long id, @Param("organizationType") OrganizationType organizationType);

    /**
     * 查询顶级组织机构
     *
     * @return 组织机构数组
     */
    @Query(value = "SELECT o FROM Organization  o where o.parent is null")
    List<Organization> findTop();

    /**
     * 查询非顶级组织机构
     *
     * @return 组织机构数组
     */
    @Query(value = "SELECT o FROM Organization  o where o.parent is not null")
    List<Organization> findNotTop();

    /**
     * 根据ID数组查询组织集合
     *
     * @param ids ID数组
     * @return 权限集合
     */
    @Query("select r from Organization as r where r.id in (:ids)")
    Set<Organization> findOrganizations(@Param("ids") long[] ids);
}
