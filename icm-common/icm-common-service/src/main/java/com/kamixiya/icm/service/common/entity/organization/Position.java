package com.kamixiya.icm.service.common.entity.organization;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 岗位实体数据类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Entity
@Table(name = "zj_sys_position")
@Setter
@Getter
public class Position extends AbstractBaseEntity {

    /**
     * 名称
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * 简称，如果不输入的话，应当和name一致
     */
    @Column(name = "short_name", length = 100, nullable = false)
    private String shortName;

    /**
     * 在OA系统中的ID
     */
    @Column(name = "oa_id", length = 40)
    private String oaId;

    /**
     * 是否可用
     */
    @Column(name = "is_available", nullable = false)
    private Boolean available = true;

    /**
     * 显示顺序
     */
    @Column(name = "show_order", nullable = false)
    private int showOrder = 0;

    /**
     * 关职的组织架构数据
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")
    private Organization organization;


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
