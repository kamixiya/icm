package com.kamixiya.icm.persistence.content.entity.budget.general;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 项目明细及说明
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Entity
@Table(name = "zj_icm_project_general_detail")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class GeneralProjectDetail extends AbstractBaseEntity {
    /**
     * 项目库
     */
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private GeneralProject project;

    /**
     * 项目编号
     */
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 支出简介(项目名称？)
     */
    @Column(name = "introduction", length = 200)
    private String introduction;

    /**
     * 支出明细
     */
    @Column(name = "detail")
    private String detail;

    /**
     * 预算金额
     */
    @Column(name = "amount")
    private Double amount;

    /**
     * 预算依据及说明     */
    @Column(name = "basis", length = 2000)
    private String basis;

    /**
     * 重要说明
     */
    @Column(name = "remark", length = 200)
    private String remark;

    /**
     * 是否可计划
     */
    @Column(name = "available_plan")
    private Boolean availablePlan = true;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}