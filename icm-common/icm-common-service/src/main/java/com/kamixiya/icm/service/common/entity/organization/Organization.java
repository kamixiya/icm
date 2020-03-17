package com.kamixiya.icm.service.common.entity.organization;

import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.service.common.entity.AbstractTreeEntity;
import com.kamixiya.icm.service.common.entity.security.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 组织架构的树型结构实体类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Entity
@Table(name = "zj_sys_organization", indexes = {
        @Index(name = "idx_organization_tree_path", columnList = "tree_path"),
        @Index(name = "idx_organization_parent_id", columnList = "parent_id")
})
@Getter
@Setter
public class Organization extends AbstractTreeEntity<Organization> {
    /**
     * 组织架构的名称，与关联的单位，部门或岗位相同
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * 简称，如果不输入，应当与name保持一致
     */
    @Column(name = "short_name", length = 100, nullable = false)
    private String shortName;

    /**
     * 组织架构类型, 标识关联是单位，部门，还是岗位
     */
    @Column(name = "organization_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrganizationType organizationType;

    /**
     * 是否可用，与关联的对象相同
     */
    @Column(name = "is_available", nullable = false)
    private Boolean available = true;

    /**
     * 所关联的员工
     */
    @ManyToMany(mappedBy = "organizations")
    @OrderBy("show_order asc")
    private Set<Employee> employees = new LinkedHashSet<>();

    /**
     * 关联的单位，如果类型是单位的话
     */
    @OneToOne(mappedBy = "organization")
    private Unit unit;

    /**
     * 关联的部门，如果类型是部门的话
     */
    @OneToOne(mappedBy = "organization")
    private Department department;

    /**
     * 关联的岗位，如果类型是岗位的话
     */
    @OneToOne(mappedBy = "organization")
    private Position position;

    /**
     * 是否内部
     */
    @Column(name = "is_interior", nullable = false)
    private Boolean interior = true;


    /**
     * 关联角色
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "zj_sys_organization_role", joinColumns = @JoinColumn(name = "organization_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OrderBy("name asc")
    private Set<Role> roles = new LinkedHashSet<>();

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
