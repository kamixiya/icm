package com.kamixiya.icm.persistence.content.entity.revenue.Registration;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 银行账户登记附件
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_revenue_bank_account_registration_attach", indexes = {
        @Index(name = "idx_revenue_bank_account_registration_attach", columnList = "name")
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class BankAccountRegistrationAttach extends AbstractBaseEntity {

    /**
     * 银行账户登记
     */
    @ManyToOne
    @JoinColumn(name = "bank_account_registration_id", nullable = false)
    private BankAccountRegistration bankAccountRegistration;

    /**
     * 附件名称
     */
    @Column(name = "name", length = 200)
    private String name;

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;

    /**
     * 附件
     */
    @ManyToMany
    @JoinTable(
            name = "zj_icm_revenue_bank_account_registration_attach_file",
            joinColumns = @JoinColumn(name = "attach_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<SystemFile> files = new ArrayList<>();
}
