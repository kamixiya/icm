package com.kamixiya.icm.service.common.entity.security;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 角色的实体类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Entity
@Table(name = "zj_sys_role")
@Getter
@Setter
public class Role extends AbstractBaseEntity {

    /**
     * 名称, 要求唯一性
     */
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    /**
     * 备注，说明该Role应当分配给什么样的人使用
     */
    @Column(name = "remark", length = 500)
    private String remark;


    /**
     * 角色所包含的用户
     */
    @ManyToMany(mappedBy = "roles")
    @OrderBy("name asc")
    private Set<User> users = new LinkedHashSet<>();

    /**
     * 角色所包含的组织
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<Organization> organizations = new LinkedHashSet<>();

    /**
     * 角色关联的权限
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "zj_sys_role_authority", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
    @OrderBy("name asc")
    private Set<Authority> authorities = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
