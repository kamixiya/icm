package com.kamixiya.icm.persistence.content.entity.purchase;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 指标信息
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_purchase_report_index")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PurchaseReportIndex extends PurchaseReportIndexBaseEntity {
    /**
     * 采购申请
     */
    @ManyToOne
    @JoinColumn(name = "purchase_report_id", nullable = false)
    private PurchaseReport purchaseReport;
}
