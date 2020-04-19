package com.kamixiya.icm.persistence.content.entity.contract;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 合同——参与方信息实体
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class ContractParticipantBaseEntity extends AbstractBaseEntity {
    /**
     * 参与方名称
     */
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    /**
     * 单位名称
     */
    @Column(name = "unit_name", nullable = false, length = 200)
    private String unitName;

    /**
     * 经办人
     */
    @Column(name = "trustees", nullable = false, length = 50)
    private String trustees;

    /**
     * 联系方式
     */
    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
