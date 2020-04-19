package com.kamixiya.icm.persistence.content.entity.payment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 支出详情
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_meeting_detail")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportMeetingDetail extends PaymentReportDetailBaseEntity{
    /**
     * 事前资金申请——会议费
     */
    @ManyToOne
    @JoinColumn(name = "meeting_id", nullable = false)
    private PaymentReportMeeting meeting;

    /**
     * 在途金额
     */
    @Column(name = "passage_amount")
    private Double passageAmount = 0.0;
}
