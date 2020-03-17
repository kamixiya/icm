package com.kamixiya.icm.service.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 所有实体的基类，包含实体的共有字段
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@MappedSuperclass
@Filter(name = "PathFilter",
        condition = "(filter_path in (:paths))")
@FilterDef(name = "PathFilter", parameters = {
        @ParamDef(name = "paths", type = "string")
})
@Filter(name = "CreatorFilter", condition = "created_by = :creatorId")
@FilterDef(name = "CreatorFilter", parameters = {
        @ParamDef(name = "creatorId", type = "long")
})

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractBaseEntity extends AbstractBaseEntityDetail {

    /**
     * 根据创建者所属组织由系统自动生成的过滤路径，用于数据范围过滤, 为null或""表示没有。
     * 如果创建数据时此字段为空，则该数据会被替换为当前登录者的过滤路径
     */
    @Column(name = "filter_path", length = 380)
    private String filterPath;

}