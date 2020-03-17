package com.kamixiya.icm.service.common.entity.organization;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 单位实体数据类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Entity
@Table(name = "zj_sys_unit")
@Setter
@Getter
public class Unit extends AbstractBaseEntity {

    /**
     * 纳税人识别号
     */
    @Column(name = "taxpayer_identification_number", length = 100)
    private String taxpayerIdentificationNumber;

    /**
     * 传真号码
     */
    @Column(name = "fax_number", length = 100)
    private String faxNumber;

    /**
     * 邮政编码
     */
    @Column(name = "postal_code", length = 100)
    private String postalCode;


    /**
     * 单位地址
     */
    @Column(name = "unit_address", length = 100)
    private String unitAddress;

    /**
     * 固定电话
     */
    @Column(name = "telephone", length = 20)
    private String telephone;

    /**
     * 备注
     */
    @Column(name = "remark", length = 2000)
    private String remark;

    /**
     * 开户银行
     */
    @Column(name = "deposit_bank", length = 100)
    private String depositBank;


    /**
     * 名称
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * 简称，如果不输入，应当与name一致
     */
    @Column(name = "short_name", length = 100, nullable = false)
    private String shortName;

    /**
     * 单位编码
     */
    @Column(name = "unit_number", length = 40, nullable = false, unique = true)
    private String unitNumber;

    /**
     * 单位性质，对应字典值UNIT_NATURE
     */
    @Column(name = "unit_nature", length = 100)
    private String unitNature;

    /**
     * 在OA系统中的ID
     */
    @Column(name = "oa_id", length = 40)
    private String oaId;

    /**
     * 是否可用
     */
    @Column(name = "is_available", nullable = false)
    private Boolean available = true;

    /**
     * 显示顺序
     */
    @Column(name = "show_order", nullable = false)
    private int showOrder = 0;

    /**
     * 关联的组织架构
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
