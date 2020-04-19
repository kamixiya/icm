package com.kamixiya.icm.persistence.content.entity.contract;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 合同付款信息实体基本类
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class ContractPaymentBaseEntity extends AbstractBaseEntity {
    /**
     * 付款阶段
     */
    @Column(name = "stage", nullable = false, length = 100)
    private String stage;

    /**
     * 付款条件
     */
    @Column(name = "terms", nullable = false, length = 200)
    private String terms;

    /**
     * 付款比例(%),全部项之和为100%
     */
    @Column(name = "rate", nullable = false)
    private Double rate;

    /**
     * 付款年份
     */
    @Column(name = "year", nullable = false, length = 4)
    private String year;

    /**
     * 预付款时间
     */
    @Column(name = "pay_date", nullable = false)
    private Date payDate;

    /**
     * 金额(元)
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
