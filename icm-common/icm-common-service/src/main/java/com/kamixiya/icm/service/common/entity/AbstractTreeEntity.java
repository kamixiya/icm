package com.kamixiya.icm.service.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 树结构的基础类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractTreeEntity<T extends AbstractTreeEntity>  extends AbstractBaseEntity {

    /**
     * 结点路径，格式：上上级线点ID-上级结点ID-本身的ID-，保留最后的"-"是为了方便组装查找条件,
     * 目前实体ID采用Long型，每个结点会占用19个字节，所以树的层次最多去到20层，
     * 例如：204043318363111424-204043318363111425-
     */
    @Column(name = "tree_path", nullable = false)
    private String treePath = "";

    /**
     * 结点的级次，标识结点在树上的层级
     */
    @Column(name = "tree_level", nullable = false)
    private int treeLevel = 1;

    /**
     * 结点在该级的显示顺序
     */
    @Column(name = "show_order", nullable = false)
    private Integer showOrder = 0;

    /**
     * 父结点，第一层结点的父结点为null
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private T parent;

    /**
     * 子结点列表
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy("show_order ASC")
    private Set<T> children = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
