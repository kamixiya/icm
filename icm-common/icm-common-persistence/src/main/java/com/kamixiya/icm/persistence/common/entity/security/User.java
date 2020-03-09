package com.kamixiya.icm.persistence.common.entity.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import javax.persistence.*;

/**
 * 用户
 *
 * @author Zhu Jie
 * @date 2020/3/8
 */
@Entity
@Table(name = "zj_sys_user")
@Getter
@Setter
public class User {

    /**
     * 主键，新增时应当为null，受限javascript的long型数据精度问题，DTO中需转换为字符串类型
     */
    @Id
    @GeneratedValue(generator = "system_uuid")
    private Long id;

    /**
     * 登录账号, 输入时，要求检查唯一性
     */
    @Column(name = "account", length = 20, unique = true, nullable = false)
    private String account;

    /**
     * 姓名
     */
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    /**
     * 本地密码, 只有localAccount为true才有效
     */
    @Column(name = "password", length = 60)
    private String password;

    /**
     * 最后修改密码的时间, 只有localAccount为true才有效
     */
    @Column(name = "change_pwd_time")
    private Date changePasswordTime;

    /**
     * 密码失效时间，如果为null，则表示永不失效
     * 只有localAccount为true才有效
     */
    @Column(name = "password_expire_date")
    private Date passwordExpireDate;

    /**
     * 账号是否已激活, 用于临时锁定账号，主要用于特殊验证（例如，邮箱激活）
     * 只有localAccount为true才有效
     */
    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    /**
     * 账号的有效期，如果为null，则表示永久有效，主要用于设置系统临时访问账号
     * 只有localAccount为true才有效
     */
    @Column(name = "account_expire_date")
    private Date accountExpireDate;

    /**
     * 连续输错密码的次数, 如果超过设定次数，账号将会被锁住，只要输对密码就会清0
     * 只有localAccount为true才有效
     */
    @Column(name = "wrong_pwd_count")
    private Integer wrongPasswordCount = 0;

    /**
     * 最后输错密码时间，如果输入正确密码后，该数据会被设置为null
     * 只有localAccount为true才有效
     */
    @Column(name = "last_wrong_pwd_time")
    private Date lastWrongPasswordTime;

    /**
     * 是否本地登录账号, 如果是则使用password来验证密码， 如不是，则使用其他方式
     */
    @Column(name = "is_local_account", nullable = false)
    private Boolean localAccount = true;

    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
