package com.kamixiya.icm.persistence.content.entity.contract;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ContractAttach
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Entity
@Table(name = "zj_icm_contract_conclusion_attach")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class ContractConclusionAttach extends AbstractBaseEntity {
    /**
     * 合同订立
     */
    @ManyToOne
    @JoinColumn(name = "conclusion_id", nullable = false)
    private ContractConclusion contractConclusion;

    /**
     * 附件类型
     */
    @Column(name = "file_type", nullable = false)
    private String fileType;

    /**
     * 附件
     */
    @ManyToMany
    @JoinTable(name = "zj_icm_contract_conclusion_attach_file", joinColumns = @JoinColumn(name = "attach_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<SystemFile> files = new ArrayList<>();

    /**
     * 排序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}
