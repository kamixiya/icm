package com.kamixiya.icm.persistence.content.entity.contract;

import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReport;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.security.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 合同订立实体
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_contract_conclusion", indexes = {
        @Index(name = "udx_icm_contract_conclusion", columnList = "code", unique = true)
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class ContractConclusion extends AbstractBaseEntity {

    /**
     * 状态
     */
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateType state;

    /**
     * 申请单号，自动生成
     */
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    /**
     * 合同编号，手输
     */
    @Column(name = "contract_no", nullable = false, length = 50)
    private String contractNo;

    /**
     * 申请日期
     */
    @Column(name = "apply_date", nullable = false)
    private Date applyDate;

    /**
     * 合同年度，只读
     */
    @Column(name = "year", nullable = false, length = 4)
    private String year;

    /**
     * 合同名称
     */
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    /**
     * 合同开始时间
     */
    @Column(name = "begin_date", nullable = false)
    private Date beginDate;

    /**
     * 合同结束时间
     */
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    /**
     * 是否为重大合同
     */
    @Column(name = "is_important", nullable = false)
    private Boolean important;

    /**
     * 是否关联采购
     */
    @Column(name = "is_purchase", nullable = false)
    private Boolean purchase;

    /**
     * 合同签订依据及主要内容
     */
    @Column(name = "basis", length = 2000)
    private String basis;

    /**
     * 备注
     */
    @Column(name = "remark", length = 2000)
    private String remark;

    /**
     * 合同总额(元)
     */
    @Column(name = "total")
    private Double total;

    /**
     * 申请金额合计
     */
    @Column(name = "apply_total")
    private Double applyTotal;


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
     * 填表人
     */
    @ManyToOne
    @JoinColumn(name = "declarer_id", nullable = false)
    private User declarer;

    /**
     * 关联采购备案单
     */
    @ManyToOne
    @JoinColumn(name = "purchase_report_id")
    private PurchaseReport purchaseReport;

    /**
     * 指标信息
     */
    @OneToMany(mappedBy = "contractConclusion")
    @OrderBy("showOrder asc ")
    private Set<ContractConclusionIndex> indexes = new LinkedHashSet<>();

    /**
     * 合同参与方信息
     */
    @OneToMany(mappedBy = "contractConclusion")
    @OrderBy("showOrder asc ")
    private Set<ContractConclusionParticipant> participants = new LinkedHashSet<>();

    /**
     * 收款人银行账户信息
     */
    @OneToMany(mappedBy = "contractConclusion")
    @OrderBy("showOrder asc ")
    private Set<ContractConclusionPayment> payments = new LinkedHashSet<>();

    /**
     * 收款人银行账户信息
     */
    @OneToMany(mappedBy = "contractConclusion")
    @OrderBy("showOrder asc ")
    private Set<ContractConclusionPayee> payees = new LinkedHashSet<>();

    /**
     * 附件信息
     */
    @OneToMany(mappedBy = "contractConclusion")
    @OrderBy("showOrder asc ")
    private Set<ContractConclusionAttach> attaches = new LinkedHashSet<>();
}
