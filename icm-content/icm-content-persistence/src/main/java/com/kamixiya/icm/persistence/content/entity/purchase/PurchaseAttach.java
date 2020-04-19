package com.kamixiya.icm.persistence.content.entity.purchase;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购计划附件详细信息
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */

@Entity
@Table(name = "zj_icm_purchase_attach")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PurchaseAttach extends AbstractBaseEntity {
    /**
     * 采购申请
     */
    @ManyToOne
    @JoinColumn(name = "purchase_report_id", nullable = false)
    private PurchaseReport purchaseReport;

    /**
     * 附件类型
     */
    @Column(name = "type", nullable = false)
    private String fileType;

    /**
     * 附件
     */
    @ManyToMany
    @JoinTable(name = "zj_icm_purchase_attach_file", joinColumns = @JoinColumn(name = "attach_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<SystemFile> files = new ArrayList<>();

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;

}
