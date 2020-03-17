package com.kamixiya.icm.service.common.entity.security;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 权限的实体类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@ApiModel(description = "权限类")
@Entity
@Table(name = "zj_sys_authority")
@Getter
@Setter
public class Authority extends AbstractBaseEntity {
    /**
     * 权限编号, 要求唯一性。需要使用"数据类型_操作类型"的形式
     * 操作类型为CRUD, 建议使用CREATE, RETRIEVE, UPDATE, DELETE(请使用\"数据类型_操作类型\"的形式")
     */
    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    /**
     * 权限名称
     */
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    /**
     * 权限所属的类型
     */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    /**
     * 拥有该权限的所有角色，null时表示数据没有传输
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
    @OrderBy("name asc")
    private Set<Role> roles = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
