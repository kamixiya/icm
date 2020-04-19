package com.kamixiya.icm.persistence.content.entity.payment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 事前资金申请——差旅费——费用详情
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_travel_detail")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportTravelDetail extends PaymentReportDetailBaseEntity {
    /**
     * 事前资金申请——差旅费
     */
    @ManyToOne
    @JoinColumn(name = "travel_expense_id", nullable = false)
    private PaymentReportTravelExpense travelExpense;

    /**
     * 职级，对应字典表类型：USER_LEVEL
     */
    @Column(name = "level", nullable = false)
    private String level;

    /**
     * 人数
     */
    @Column(name = "participant_number", nullable = false)
    private Integer participantNumber;

    /**
     * 交通工具，对应字典表类型：TRANSPORTATION_FACILITY
     */
    @Column(name = "transportation_facility", nullable = false)
    private String transportationFacility;

    /**
     * 在途金额
     */
    @Column(name = "passage_amount")
    private Double passageAmount = 0.0;
}
