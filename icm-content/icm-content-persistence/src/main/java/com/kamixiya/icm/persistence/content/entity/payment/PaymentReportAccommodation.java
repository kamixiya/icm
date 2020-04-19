package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 事前资金申请——住宿安排实体
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_accommodation")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportAccommodation extends AbstractBaseEntity {
    /**
     * 事前资金申请
     */
    @ManyToOne
    @JoinColumn(name = "payment_report_id", nullable = false)
    private PaymentReport paymentReport;

    /**
     * 宾馆名称
     */
    @Column(name = "hotel_name", nullable = false, length = 200)
    private String hotelName;

    /**
     * 普通套间数
     */
    @Column(name = "general_number", nullable = false)
    private Integer generalNumber;

    /**
     * 标准间数
     */
    @Column(name = "standard_number", nullable = false)
    private Integer standardNumber;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
