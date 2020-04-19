package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.persistence.content.entity.contract.ContractPaymentBaseEntity;
import com.kamixiya.icm.persistence.content.entity.contract.ContractPaymentState;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 事前资金申请——合同付款信息
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_payment_report_contract")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportContract extends ContractPaymentBaseEntity {
    /**
     * 事前资金申请
     */
    @ManyToOne
    @JoinColumn(name = "payment_report_id", nullable = false)
    private PaymentReport paymentReport;

    /**
     * 合同付款信息ID
     */
    @Column(name = "contract_payment_id", nullable = false)
    private Long contractPaymentId;

    /**
     * 支付状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_state")
    private ContractPaymentState paymentState;

    /**
     * 申请支付，一次只能申请一个支付
     */
    @Column(name = "is_request_payment")
    private Boolean requestPayment = false;
}
