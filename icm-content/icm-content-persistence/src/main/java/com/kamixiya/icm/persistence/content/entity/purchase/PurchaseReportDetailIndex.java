package com.kamixiya.icm.persistence.content.entity.purchase;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 采购申请——指标详细信息
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_purchase_report_detail_index")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class PurchaseReportDetailIndex extends PurchaseReportIndexBaseEntity {
    /**
     * 品目——采购内容
     */
    @ManyToOne
    @JoinColumn(name = "detail_id")
    private PurchaseReportDetail purchaseReportDetail;
}
