package com.kamixiya.icm.persistence.content.entity.contract;

import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * 合同——指标信息实体
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class ContractIndexBaseEntity extends AbstractBaseEntity {
    /**
     * 关联的指标
     */
    @ManyToOne
    @JoinColumn(name = "index_id", nullable = false)
    private IndexLibrary index;

    /**
     * 采购金额
     */
    @Column(name = "purchase_amount")
    private Double purchaseAmount;

    /**
     * 申请金额
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

    /**
     * 已支付金额
     */
    @Column(name = "paid_amount", nullable = false)
    private Double paidAmount;

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
