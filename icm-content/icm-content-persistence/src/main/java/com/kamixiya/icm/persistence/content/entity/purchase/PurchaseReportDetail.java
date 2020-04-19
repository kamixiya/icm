package com.kamixiya.icm.persistence.content.entity.purchase;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购内容
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_purchase_report_detail")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PurchaseReportDetail extends AbstractBaseEntity {

    /**
     * 采购申请
     */
    @ManyToOne
    @JoinColumn(name = "purchase_report_id", nullable = false)
    private PurchaseReport purchaseReport;

    /**
     * 内容名称
     */
    @Column(name = "purchase_detail", nullable = false)
    private String purchaseDetail;

    /**
     * 规格型号
     */
    @Column(name = "spec", nullable = false, length = 200)
    private String spec;

    /**
     * 参考单价
     */
    @Column(name = "price", nullable = false)
    private Double price;

    /**
     * 合计数量
     */
    @Column(name = "quantity_total", nullable = false)
    private Double quantityTotal;

    /**
     * 采购方式
     */
    @Column(name = "purchase_type", nullable = false)
    private String purchaseType;

    /**
     * 采购金额合计
     */
    @Column(name = "total")
    private Double total;

    /**
     * 指标信息
     */
    @OneToMany(mappedBy = "purchaseReportDetail")
    private List<PurchaseReportDetailIndex> indexes = new ArrayList<>();


    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;

}
