package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusion;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReport;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.security.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 事前资金申请实体
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report", indexes = {
        @Index(name = "udx_icm_payment_report", columnList = "code", unique = true)
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReport extends AbstractBaseEntity {

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
     * 费用类型
     */
    @Column(name = "expense_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    /**
     * 申请时间
     */
    @Column(name = "apply_date", nullable = false)
    private Date applyDate;

    /**
     * 是否关联采购,会议费资金申请时非空
     */
    @Column(name = "is_purchase")
    private Boolean purchase;

    /**
     * 是否释放
     */
    @Column(name = "is_release")
    private Boolean isRelease;

    /**
     * 业务标题
     */
    @Column(name = "title", length = 200)
    private String title;

    /**
     * 职称
     */
    @Column(name = "professional_title", length = 50)
    private String professionalTitle;

    /**
     * 接待类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "reception_type")
    private ReceptionType receptionType;

    /**
     * 贵宾人数
     */
    @Column(name = "guest_number")
    private Integer guestNumber;

    /**
     * 陪客人数
     */
    @Column(name = "entertain_number")
    private Integer entertainNumber;

    /**
     * 用车开始时间，费用类型是公务用车事前资金时非空
     */
    @Column(name = "begin_date")
    private Date beginDate;

    /**
     * 用车结束时间，费用类型是公务用车事前资金时非空
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 审结时间
     */
    @Column(name = "complete_date")
    private Date completeDate;

    /**
     * 预计产生费用，费用类型是公务用车事前资金时非空
     */
    @Column(name = "plan_amount")
    private Double planAmount;

    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;

    /**
     * 请示内容
     */
    @Column(name = "content", length = 500)
    private String content;

    /**
     * 用车事由
     */
    @Column(name = "car_reason", length = 500)
    private String carReason;

    /**
     * 合计申请金额（元）
     */
    @Column(name = "total")
    private Double total;

    /**
     * 合计申请金额（元），多类经费使用该字段
     */
    @Column(name = "multiple_total")
    private Double multipleTotal;

    /**
     * 关联采购备案单（会议费）
     */
    @ManyToOne
    @JoinColumn(name = "purchase_report_id")
    private PurchaseReport purchaseReport;

    /**
     * 合同,合同费用时非空
     */
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractConclusion contract;

    /**
     * 关联采购（非会议费）
     */
    @ManyToOne
    @JoinColumn(name = "purchase_report_id2")
    private PurchaseReport purchaseReport2;

    /**
     * 会议费
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportMeeting> meetings = new LinkedHashSet<>();

    /**
     * 培训费
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportTraining> trainings = new LinkedHashSet<>();

    /**
     * 因公出国（境）经费
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportAbroadExpense> abroadExpenses = new LinkedHashSet<>();

    /**
     * 接待对象
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportGuest> guests = new LinkedHashSet<>();

    /**
     * 主要行程安排
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportItinerary> itineraries = new LinkedHashSet<>();

    /**
     * 住宿安排
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportAccommodation> accommodations = new LinkedHashSet<>();

    /**
     * 接待费用
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportGuestExpense> guestExpenses = new LinkedHashSet<>();

    /**
     * 差旅费
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportTravelExpense> travelExpenses = new LinkedHashSet<>();

    /**
     * 劳务费
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportLabourExpense> labourExpenses = new LinkedHashSet<>();

    /**
     * 合同付款信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportContract> contracts = new LinkedHashSet<>();

    /**
     * 事前资金详细信息（一般经费）
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportDetail> paymentDetails = new LinkedHashSet<>();

    /**
     * 附件信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    private Set<PaymentReportAttach> attaches = new LinkedHashSet<>();

    /**
     * 会议费指标信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    @Where(clause = "expense_type = 'MEETING'")
    private Set<PaymentReportIndex> meetingIndexes = new LinkedHashSet<>();

    /**
     * 培训费指标信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    @Where(clause = "expense_type = 'TRAINING'")
    private Set<PaymentReportIndex> trainingIndexes = new LinkedHashSet<>();

    /**
     * 因公出国（境）指标信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    @Where(clause = "expense_type = 'ABROAD'")
    private Set<PaymentReportIndex> abroadIndexes = new LinkedHashSet<>();

    /**
     * 公务接待指标信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    @Where(clause = "expense_type = 'OFFICIAL'")
    private Set<PaymentReportIndex> officialIndexes = new LinkedHashSet<>();

    /**
     * 差旅费指标信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    @Where(clause = "expense_type = 'TRAVEL'")
    private Set<PaymentReportIndex> travelIndexes = new LinkedHashSet<>();

    /**
     * 劳务费指标信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    @Where(clause = "expense_type = 'SERVICE'")
    private Set<PaymentReportIndex> serviceIndexes = new LinkedHashSet<>();

    /**
     * 公务用车经指标信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    @Where(clause = "expense_type = 'OFFICIAL_CAR'")
    private Set<PaymentReportIndex> officialCarIndexes = new LinkedHashSet<>();

    /**
     * 合同资金指标信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    @Where(clause = "expense_type = 'CONTRACT'")
    private Set<PaymentReportIndex> contractIndexes = new LinkedHashSet<>();

    /**
     * 非合同采购指标信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    @Where(clause = "expense_type = 'PURCHASE'")
    private Set<PaymentReportIndex> purchaseIndexes = new LinkedHashSet<>();

    /**
     * 一般经费指标信息
     */
    @OneToMany(mappedBy = "paymentReport")
    @OrderBy("show_order asc ")
    @Where(clause = "expense_type = 'GENERAL'")
    private Set<PaymentReportIndex> generalIndexes = new LinkedHashSet<>();
}
