package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * 支出详情基础类
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportDetailBaseEntity extends AbstractBaseEntity {
    /**
     * 支出内容
     */
    @Column(name = "content", nullable = false, length = 200)
    private String content;

    /**
     * 支出类型
     */
    @Column(name = "expense_type_detail")
    private String expenseTypeDetail;

    /**
     * 参考标准（元/人天）
     */
    @Column(name = "reference_standard", nullable = false)
    private Double referenceStandard;

    /**
     * 申请金额（元/人天）
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

    /**
     * 申请总金额
     */
    @Column(name = "total", nullable = false)
    private Double total;

    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
