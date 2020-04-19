package com.kamixiya.icm.persistence.content.entity.contract;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 收款人银行账户信息实体
 *
 * @author maqionggui
 * @date 2019/11/15
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class ContractPayeeBaseEntity extends AbstractBaseEntity {
    /**
     * 单位名称
     */
    @Column(name = "unit_name", nullable = false, length = 200)
    private String unitName;

    /**
     * 开户银行名称
     */
    @Column(name = "bank_name", nullable = false, length = 200)
    private String bankName;

    /**
     * 银行账号
     */
    @Column(name = "account", nullable = false, length = 100)
    private String account;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
