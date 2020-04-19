package com.kamixiya.icm.persistence.content.entity.revenue.opening;

import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.security.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BankAccountOpening
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_revenue_account_opening")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class BankAccountOpening extends AbstractBaseEntity {

    /**
     * 审批状态,默认草稿状态
     */
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateType state = StateType.UNDONE;

    /**
     * 申报单位
     */
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Organization unit;

    /**
     * 申报部门
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
     * 申请单号，非空
     */
    @Column(name = "code", length = 200, nullable = false)
    private String code;

    /**
     * 申请时间，非空
     */
    @Column(name = "apply_date", nullable = false)
    private Date applyDate;

    /**
     * 账户性质
     */
    @Column(name = "account_property", length = 200, nullable = false)
    private String accountProperty;

    /**
     * 单位性质
     */
    @Column(name = "unit_property", length = 200, nullable = false)
    private String unitProperty;

    /**
     * 开户银行
     */
    @Column(name = "name_of_bank", length = 200, nullable = false)
    private String nameOfBank;

    /**
     * 开户银行全称，非空
     */
    @Column(name = "full_name_of_bank", length = 200, nullable = false)
    private String fullNameOfBank;

    /**
     * 政府依据
     */
    @Column(name = "government_basic", length = 200)
    private String governmentBasic;

    /**
     * 选择原因
     */
    @Column(name = "reason", length = 200)
    private String reason;

    /**
     * 附件信息
     */
    @OneToMany(mappedBy = "bankAccountOpening")
    @OrderBy("show_order ASC")
    private List<BankAccountOpeningAttach> bankAccountOpeningAttaches = new ArrayList<>();
}