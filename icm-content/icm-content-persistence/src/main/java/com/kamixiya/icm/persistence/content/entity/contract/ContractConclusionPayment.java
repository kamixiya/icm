package com.kamixiya.icm.persistence.content.entity.contract;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 合同订立付款信息实体
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_contract_conclusion_payment")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class ContractConclusionPayment extends ContractPaymentBaseEntity {
    /**
     * 合同订立
     */
    @ManyToOne
    @JoinColumn(name = "conclusion_id", nullable = false)
    private ContractConclusion contractConclusion;

    /**
     * 支付状态
     */
    @Column(name = "payment_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractPaymentState paymentState = ContractPaymentState.TO_PAY;
}
