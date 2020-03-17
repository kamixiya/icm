package com.kamixiya.icm.service.common.repository.organization;

import com.kamixiya.icm.service.common.entity.organization.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * EmployeeRepository
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    /**
     * 根据用户id查询员工
     *
     * @param userId 用户id
     * @return 员工实体
     */
    @Query(value = "select e from  Employee e where e.user.id= :userId")
    Employee findByUserId(@Param("userId") Long userId);

    /**
     * 根据组织Id集合查询出员工集合，并通过员工名称或员工账号过滤
     *
     * @param treePath     treePath
     * @param employeeName 员工姓名
     * @param account      员工用户账号
     * @return 员工ID集合
     */
    @Query(value = "select  e.id  from Employee e left join e.organizations os left join e.user u  WHERE os.treePath like :treePath and (e.name like :employeeName or u.account like :account ) ORDER BY os.treeLevel asc, e.showOrder asc")
    List<Long> findEmployeeIdByOrganization(@Param("treePath") String treePath, @Param("employeeName") String employeeName, @Param("account") String account);

    /**
     * 根据组织Id集合查询出员工集合，并通过员工名称或员工账号或员工状态过滤
     *
     * @param treePath     treePath
     * @param employeeName 员工姓名
     * @param account      员工用户账号
     * @param available    员工状态
     * @return 员工ID集合
     */
    @Query(value = "select  e.id  from Employee e left join e.organizations os left join e.user u  WHERE os.treePath like :treePath and (e.name like :employeeName or u.account like :account or e.available = :available) ORDER BY os.treeLevel asc, e.showOrder asc")
    List<Long> findEmployeeIdByOrganization(@Param("treePath") String treePath, @Param("employeeName") String employeeName, @Param("account") String account, @Param("available") Boolean available);


    /**
     * 根据组织Id查询出员工集合，并通过员工名称或员工账号过滤
     *
     * @param empIds 组织ID集合
     * @return 员工集合
     */
    @Query(value = "select e from Employee e where  e.id in (:empIds)")
    List<Employee> findByEmployeeIds(@Param("empIds") List<Long> empIds);


    /**
     * 根据组织Id查询出员工集合，并通过员工名称或员工账号过滤
     *
     * @param orgId        组织ID
     * @param employeeName 员工姓名
     * @param account      员工用户账号
     * @return 员工集合
     */
    @Query(value = "select  e.id  from Employee e left join e.organizations os left join e.user u  WHERE os.id = :orgId and (e.name like :employeeName or u.account like :account ) order by os.treeLevel asc, e.showOrder asc")
    List<Long> findEmployeesByOrganizationId(@Param("orgId") Long orgId, @Param("employeeName") String employeeName, @Param("account") String account);

    /**
     * 根据组织Id查询出员工集合，并通过员工名称或员工账号或员工状态过滤
     *
     * @param orgId        组织ID
     * @param employeeName 员工姓名
     * @param account      员工用户账号
     * @param available    员工状态
     * @return 员工集合
     */
    @Query(value = "select  e.id  from Employee e left join e.organizations os left join e.user u  WHERE os.id = :orgId and (e.name like :employeeName or u.account like :account or e.available = :available) order by os.treeLevel asc, e.showOrder asc")
    List<Long> findEmployeesByOrganizationId(@Param("orgId") Long orgId, @Param("employeeName") String employeeName, @Param("account") String account, @Param("available") Boolean available);

    /**
     * 通过员工编号查询员工id
     *
     * @param code 参数名
     * @return 参数ID
     */
    @Query("select e.id from Employee as e where e.code = :code")
    Long findEmployeeByCode(@Param("code") String code);
}