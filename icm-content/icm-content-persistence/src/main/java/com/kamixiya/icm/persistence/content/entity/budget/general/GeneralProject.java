package com.kamixiya.icm.persistence.content.entity.budget.general;

import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目库
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Entity
@Table(name = "zj_icm_project_general", indexes = {
        @Index(name = "udx_project_general", columnList = "code,year", unique = true),
        @Index(name = "idx_project_general", columnList = "name")
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class GeneralProject extends AbstractBaseEntity {

    /**
     * 审批状态
     */
    @Column(name = "state_type")
    @Enumerated(EnumType.STRING)
    private StateType stateType;

    /**
     * 申报单位
     */
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Organization unit;

    /**
     * 申报部门
     */
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Organization department;

    /**
     * 申报日期
     */
    @Column(name = "apply_date", nullable = false)
    private Date applyDate;

    /**
     * 年份
     */
    @Column(name = "year", nullable = false, length = 4)
    private Integer year;

    /**
     * 项目编码
     */
    @Column(name = "code", nullable = false, length = 20)
    private String code;

    /**
     * 项目名称
     */
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    /**
     * 申报类型
     */
    @Column(name = "apply_type", nullable = false)
    private String applyType;

    /**
     * 项目类别
     */
    @Column(name = "project_type", nullable = false)
    private String projectType;

    /**
     * 项目类别明细
     */
    @Column(name = "project_detailed_type", nullable = false)
    private String projectDetailedType;

    /**
     * 项目属性
     */
    @Column(name = "project_property", nullable = false)
    private String projectProperty;

    /**
     * 项目开始时间
     */
    @Column(name = "begin_time", nullable = false)
    private Date beginTime;

    /**
     * 项目结束时间
     */
    @Column(name = "end_time", nullable = false)
    private Date endTime;

    /**
     * 重要内容
     */
    @Column(name = "important_content")
    private String importantContent;

    /**
     * 主管部门
     */
    @ManyToOne
    @JoinColumn(name = "administrative_department_id", nullable = false)
    private Organization administrativeDepartment;

    /**
     * 主管部门联系人
     */
    @Column(name = "administrative_department_link", length = 200)
    private String administrativeDepartmentLink;

    /**
     * 主管部门联系电话
     */
    @Column(name = "administrative_department_telephone", length = 50)
    private String administrativeDepartmentTelephone;

    /**
     * 项目负责人
     */
    @Column(name = "project_leader", length = 200)
    private String projectLeader;

    /**
     * 项目负责人联系电话
     */
    @Column(name = "projectLeader_telephone", length = 50)
    private String projectLeaderTelephone;

    /**
     * 项目负责人手机号码
     */
    @Column(name = "projectLeader_phone", length = 50)
    private String projectLeaderPhone;

    /**
     * 项目明细及说明
     */
    @OneToMany(mappedBy = "project")
    @OrderBy("show_order ASC")
    private List<GeneralProjectDetail> projectDetails;

    /**
     * 附件信息
     */
    @OneToMany(mappedBy = "project")
    @OrderBy("show_order ASC")
    private List<GeneralProjectAttach> projectAttaches = new ArrayList<>();

}
