package com.kamixiya.icm.service.common.repository.security;

import com.kamixiya.icm.service.common.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * UserRepository
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * 通过用户的登录账号查找用户ID
     *
     * @param account 用户账号
     * @return 用户ID，否则返回null
     */
    @Query("select u.id from User as u where u.account = :account")
    Long findByAccount(@Param("account") String account);

    /**
     * 通过ID数组查询用户集合
     *
     * @param ids ID数组
     * @return 用户集合
     */
    @Query("select u from User as u where u.id in (:ids)")
    Set<User> findUsers(@Param("ids") long[] ids);
}
