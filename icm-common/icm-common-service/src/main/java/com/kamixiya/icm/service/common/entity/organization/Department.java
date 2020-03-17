package com.kamixiya.icm.service.common.entity.organization;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 部门实体数据类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Entity
@Table(name = "zj_sys_department")
@Getter
@Setter
public class Department extends AbstractBaseEntity {

    /**
     * 分管领导
     */
    @Column(name = "branch_lead_id", length = 40)
    private Long branchLeadId;

    /**
     * 部门名称
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * 部门的简称，如果不输入，应当与name一致
     */
    @Column(name = "short_name", length = 100, nullable = false)
    private String shortName;

    /**
     * 部门在OA系统中的ID
     */
    @Column(name = "oa_id", length = 40)
    private String oaId;

    /**
     * 部门是否可用状态，非可用状态表示部门可能已经撤消等情况
     */
    @Column(name = "is_available", nullable = false)
    private Boolean available = true;

    /**
     * 显示的顺序
     */
    @Column(name = "show_order", nullable = false)
    private int showOrder = 0;

    /**
     * 部门关系的组织架构信息
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
