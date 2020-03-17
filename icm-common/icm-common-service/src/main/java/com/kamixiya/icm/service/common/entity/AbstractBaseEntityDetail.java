package com.kamixiya.icm.service.common.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 所有实体的基类，包含实体的共有字段
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@MappedSuperclass
@EntityListeners({
        AuditingEntityListener.class
})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public abstract class AbstractBaseEntityDetail {

    /**
     * 主键，新增时应当为null，受限javascript的long型数据精度问题，DTO中需转换为字符串类型
     */
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "com.kamixiya.icm.core.jpa.SnowflakeIdGenerator")
    private Long id;

    /**
     * 创建数据的用户ID
     * 后台根据登录用户，自动填写
     */
    @Column(name = "created_by", updatable = false)
    @CreatedBy
    private Long createdBy;

    /**
     * 创建数据的时间，后台自动使用系统当前时间自动填写
     */
    @Column(name = "created_time", nullable = false, updatable = false)
    @CreatedDate
    private Date createdTime;

    /**
     * 最后修改者的ID，系统自动填写
     */
    @Column(name = "last_modified_by")
    @LastModifiedBy
    private Long lastModifiedBy;

    /**
     * 最后的修收时间，系统自动填写
     */
    @Column(name = "last_modified_time", nullable = false)
    @LastModifiedDate
    private Date lastModifiedTime;
}