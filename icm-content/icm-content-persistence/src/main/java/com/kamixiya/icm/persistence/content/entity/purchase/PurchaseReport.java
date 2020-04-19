package com.kamixiya.icm.persistence.content.entity.purchase;

import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.security.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 采购申请
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_purchase_report", indexes = {
        @Index(name = "udx_icm_purchase_report", columnList = "code", unique = true)
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PurchaseReport extends AbstractBaseEntity {

    /**
     * 状态
     */
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateType state;

    /**
     * 申请单号
     */
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    /**
     * 申请日期
     */
    @Column(name = "apply_date", nullable = false)
    private Date applyDate;

    /**
     * 业务标题
     */
    @Column(name = "title", nullable = false, length = 500)
    private String title;

    /**
     * 采购事由
     */
    @Column(name = "reason", length = 500)
    private String reason;

    /**
     * 业务依据及内容
     */
    @Column(name = "basis", length = 500)
    private String basis;

    /**
     * 采购本年支出总金额（元）
     */
    @Column(name = "current_year_total")
    private Double currentYearTotal;

    /**
     * 采购总金额（元）
     */
    @Column(name = "total")
    private Double total;

    /**
     * 单位
     */
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Organization unit;

    /**
     * 申请部门
     */
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Organization department;

    /**
     * 申请人
     */
    @ManyToOne
    @JoinColumn(name = "declarer_id", nullable = false)
    private User declarer;

    /**
     * 采购内容
     */
    @OneToMany(mappedBy = "purchaseReport")
    @OrderBy("show_order asc ")
    private Set<PurchaseReportDetail> details = new HashSet<>();

    /**
     * 指标信息
     */
    @OneToMany(mappedBy = "purchaseReport")
    @OrderBy("show_order asc ")
    private Set<PurchaseReportIndex> indexes = new HashSet<>();

    /**
     * 附件
     */
    @OneToMany(mappedBy = "purchaseReport")
    @OrderBy("show_order asc ")
    private Set<PurchaseAttach> attaches = new HashSet<>();

}
