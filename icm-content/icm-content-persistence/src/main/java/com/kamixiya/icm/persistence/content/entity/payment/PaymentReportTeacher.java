package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 事前资金申请——培训费——师资费实体
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_teacher")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportTeacher extends AbstractBaseEntity {
    /**
     * 培训费
     */
    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private PaymentReportTraining training;

    /**
     * 上课时间
     */
    @Column(name = "class_time", nullable = false)
    private Date classTime;

    /**
     * 课时
     */
    @Column(name = "lesson_period", nullable = false)
    private Integer lessonPeriod;

    /**
     * 授课内容
     */
    @Column(name = "content", length = 500)
    private String content;

    /**
     * 专家
     */
    @Column(name = "expert", nullable = false)
    private String expert;

    /**
     * 职称
     */
    @Column(name = "professional_title", length = 50)
    private String professionalTitle;

    /**
     * 参考标准（元/人课时）
     */
    @Column(name = "reference_standard", nullable = false)
    private Double referenceStandard;

    /**
     * 申请金额（元/人课时）
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

    /**
     * 在途金额
     */
    @Column(name = "passage_amount")
    private Double passageAmount = 0.0;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
