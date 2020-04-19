package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 事前资金申请——因公出国（境）经费实体
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_abroad_expense")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportAbroadExpense extends AbstractBaseEntity {
    /**
     * 事前资金申请
     */
    @ManyToOne
    @JoinColumn(name = "payment_report_id", nullable = false)
    private PaymentReport paymentReport;

    /**
     * 国家
     */
    @Column(name = "country", nullable = false)
    private String country;

    /**
     * 地区，国家下面有地区一定要选地区
     */
    @Column(name = "regional")
    private String regional;

    /**
     * 人数
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
     * 申请金额合计
     */
    @Column(name = "total")
    private Double total = 0.00;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;

    /**
     * 支出详情
     */
    @OneToMany(mappedBy = "abroadExpense")
    @OrderBy("show_order asc ")
    private Set<PaymentReportAbroadDetail> paymentDetails = new LinkedHashSet<>();
}
