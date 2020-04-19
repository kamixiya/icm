package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.security.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

/**
 * 事前资金申请——差旅费
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_travel_expense")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportTravelExpense extends AbstractBaseEntity {
    /**
     * 事前资金申请
     */
    @ManyToOne
    @JoinColumn(name = "payment_report_id", nullable = false)
    private PaymentReport paymentReport;

    /**
     * 出差人员列表
     */
    @ManyToMany
    @JoinTable(name = "zj_icm_payment_report_travel_expense_user", joinColumns = @JoinColumn(name = "travel_expense_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    /**
     * 地区
     */
    @Column(name = "regional", nullable = false)
    private String regional;

    /**
     * 出发地点
     */
    @Column(name = "startArea", nullable = false)
    private String startArea;

    /**
     * 总人数
     */
    @Column(name = "participant_number", nullable = false)
    private Integer participantNumber;

    /**
     * 开始时间
     */
    @Column(name = "begin_date", nullable = false)
    private Date beginDate;

    /**
     * 结束时间
     */
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    /**
     * 天数
     */
    @Column(name = "duration", nullable = false)
    private Integer duration;

    /**
     * 接待单位是否承担食宿
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "accommodate_type", nullable = false)
    private AccommodateType accommodateType;

    /**
     * 是否包车
     */
    @Column(name = "is_chartered_bus", nullable = false)
    private Boolean charteredBus;

    /**
     * 出差事由
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "travel_reason", nullable = false)
    private TravelReason travelReason;

    /**
     * 乘坐飞机理由
     */
    @Column(name = "ride_plane_reason", length = 1000)
    private String ridePlaneReason;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;

    /**
     * 费用详情
     */
    @OneToMany(mappedBy = "travelExpense")
    @OrderBy("show_order asc ")
    private Set<PaymentReportTravelDetail> paymentDetails = new LinkedHashSet<>();
}
