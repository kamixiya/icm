package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 事前资金申请——培训费实体
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_training")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportTraining extends AbstractBaseEntity {
    /**
     * 事前资金申请
     */
    @ManyToOne
    @JoinColumn(name = "payment_report_id", nullable = false)
    private PaymentReport paymentReport;

    /**
     * 培训班名称
     */
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    /**
     * 培训类型，对应字典表类型: PROJECT_TRAINING_TYPE
     */
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 是否定点
     */
    @Column(name = "is_appoint", nullable = false)
    private Boolean appoint;

    /**
     * 供应商,定点时非空
     */
    @Column(name = "supplier")
    private String supplier;

    /**
     * 培训地点，非定点时非空
     */
    @Column(name = "place", length = 500)
    private String place;

    /**
     * 培训人数
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
    private Double total;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;

    /**
     * 师资费实体
     */
    @OneToMany(mappedBy = "training")
    @OrderBy("show_order asc ")
    private List<PaymentReportTeacher> teachers;

    /**
     * 支出详情
     */
    @OneToMany(mappedBy = "training")
    @OrderBy("show_order asc ")
    private Set<PaymentReportTrainingDetail> paymentDetails = new LinkedHashSet<>();
}
