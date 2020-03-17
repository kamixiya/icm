package com.kamixiya.icm.service.common.entity.organization;

import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.model.organization.employee.GenderType;
import com.kamixiya.icm.service.common.entity.security.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 员工的数据实体类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */

@Entity
@Table(name = "zj_sys_employee")
@Getter
@Setter
public class Employee extends AbstractBaseEntity {

    /**
     * 员工编号
     */
    @Column(name = "code", length = 50)
    private String code;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 50)
    private String email;

    /**
     * 联系方式
     */
    @Column(name = "contact_information", length = 50)
    private String contactInformation;


    /**
     * 员工姓名
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 出生日期
     */
    @Column(name = "birth_date")
    private Date birthDate;

    /**
     * 性别
     */
    @Column(name = "gender", length = 20)
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    /**
     * 入职日期
     */
    @Column(name = "join_time")
    private Date joinTime;

    /**
     * 员工在OA系统中的ID
     */
    @Column(name = "oa_id", length = 40)
    private String oaId;

    /**
     * 员工在OA系统中的登录账号
     */
    @Column(name = "oa_account", length = 30)
    private String oaAccount;

    /**
     * 员工在OA系统中的密码
     */
    @Column(name = "oa_password", length = 60)
    private String oaPassword;

    /**
     * 员工的可用状态
     */
    @Column(name = "is_available", nullable = false)
    private Boolean available = true;

    /**
     * 显示顺序
     */
    @Column(name = "show_order", nullable = false)
    private int showOrder = 0;

    /**
     * 所属的组织机构
     */
    @ManyToMany
    @JoinTable(name = "zj_sys_emp_org", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "organization_id"))
    @OrderBy("show_order asc")
    private Set<Organization> organizations = new LinkedHashSet<>();

    /**
     * 员工关联的系统账号
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 员工默认的组织
     */
    @Column(name = "default_organization_id", length = 40)
    private Long defaultOrganizationId;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
