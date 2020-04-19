package com.kamixiya.icm.persistence.content.entity.revenue.Registration;

import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.revenue.AccountState;
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
 * 银行账户登记
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_revenue_account_registration", indexes = {
        @Index(name = "idx_revenue_account", columnList = "account_name")
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class BankAccountRegistration extends AbstractBaseEntity {

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
     * 登记人
     */
    @ManyToOne
    @JoinColumn(name = "registrant_id", nullable = false)
    private User registrant;

    /**
     * 法定代表人，非空
     */
    @Column(name = "legal_representative", length = 200, nullable = false)
    private String legalRepresentative;

    /**
     * 账户名称，非空
     */
    @Column(name = "account_name", length = 200, nullable = false)
    private String accountName;

    /**
     * 开户时间，非空
     */
    @Column(name = "open_time", length = 200, nullable = false)
    private Date openTime;

    /**
     * 开户银行,非空
     */
    @Column(name = "name_of_bank", length = 200, nullable = false)
    private String nameOfBank;

    /**
     * 账户有效期（年）
     */
    @Column(name = "validity_period", length = 4, nullable = false)
    private Integer validityPeriod;

    /**
     * 账号
     */
    @Column(name = "account", length = 200, nullable = false)
    private String account;

    /**
     * 账户状态
     */
    @Column(name = "account_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountState accountState;

    /**
     * 账户性质,对于字典表类型: ACCOUNT_CHARACTER
     */
    @Column(name = "account_property", length = 200)
    private String accountProperty;

    /**
     * 单位性质,对于字典表类型: UNIT_NATURE
     */
    @Column(name = "unit_property", length = 200, nullable = false)
    private String unitProperty;

    /**
     * 账户用途
     */
    @Column(name = "account_use", length = 200)
    private String accountUse;

    /**
     * 账户币种，字典值ACCOUNT_CURRENCY
     */
    @Column(name = "account_currency", length = 200)
    private String accountCurrency;

    /**
     * 销户原因
     */
    @Column(name = "reason_of_cancellation", length = 200)
    private String reasonOfCancellation;

    /**
     * 开户许可证号
     */
    @Column(name = "account_license", length = 200)
    private String accountLicense;

    /**
     * 开户银行全称，非空
     */
    @Column(name = "full_name_of_bank", length = 200, nullable = false)
    private String fullNameOfBank;

    /**
     * 财政批复书批号
     */
    @Column(name = "rely_number", length = 200)
    private String relyNumber;

    /**
     * 联系人
     */
    @Column(name = "account_link", length = 200)
    private String accountLink;

    /**
     * 联系电话
     */
    @Column(name = "telephone", length = 11)
    private String telephone;

    /**
     * 备注
     */
    @Column(name = "name", length = 200)
    private String remark;

    /**
     * 附件信息
     */
    @OneToMany(mappedBy = "bankAccountRegistration")
    @OrderBy("show_order ASC")
    private List<BankAccountRegistrationAttach> bankAccountRegistrationAttaches = new ArrayList<>();
}
