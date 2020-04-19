package com.kamixiya.icm.persistence.content.entity.payment;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 事前资金申请附件实体
 *
 * @author maqionggui
 * @date 2019/12/6
 */
@Entity
@Table(name = "zj_icm_payment_report_attach")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class PaymentReportAttach extends AbstractBaseEntity {
    /**
     * 事前资金申请
     */
    @ManyToOne
    @JoinColumn(name = "payment_report_id", nullable = false)
    private PaymentReport paymentReport;

    /**
     * 费用类型，多种费用类型要分开
     */
    @Column(name = "expense_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    /**
     * 附件类型
     */
    @Column(name = "file_type", nullable = false)
    private String fileType;


    /**
     * 附件
     */
    @ManyToMany
    @JoinTable(name = "zj_icm_payment_report_attach_file", joinColumns = @JoinColumn(name = "attach_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<SystemFile> files = new LinkedHashSet<>();

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
