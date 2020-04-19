package com.kamixiya.icm.persistence.content.entity.contract;

/**
 * ContractPaymentState
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
public enum ContractPaymentState {
    /**
     * 待支付
     */
    TO_PAY,
    /**
     * 在途
     */
    PAYING,
    /**
     * 已支付
     */
    PAID,
}
