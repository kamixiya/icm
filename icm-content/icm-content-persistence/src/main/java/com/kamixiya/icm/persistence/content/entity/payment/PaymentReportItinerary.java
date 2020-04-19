package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 事前资金申请——主要行程安排实体
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_itinerary")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportItinerary extends AbstractBaseEntity {
    /**
     * 事前资金申请
     */
    @ManyToOne
    @JoinColumn(name = "payment_report_id", nullable = false)
    private PaymentReport paymentReport;

    /**
     * 项目
     */
    @Column(name = "project_name", nullable = false, length = 200)
    private String projectName;

    /**
     * 时间
     */
    @Column(name = "time", nullable = false)
    private Date time;

    /**
     * 场所
     */
    @Column(name = "place", nullable = false, length = 500)
    private String place;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
