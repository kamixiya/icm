package com.kamixiya.icm.persistence.content.entity.contract;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 合同订立——指标信息实体
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_contract_conclusion_index")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class ContractConclusionIndex extends ContractIndexBaseEntity {
    /**
     * 合同订立
     */
    @ManyToOne
    @JoinColumn(name = "conclusion_id", nullable = false)
    private ContractConclusion contractConclusion;
}
