package com.kamixiya.icm.service.common.utils;

import com.kamixiya.icm.service.common.entity.AbstractTreeEntity;

/**
 * 树形工具类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
public class TreeUtil {

    private TreeUtil() {
    }

    /**
     * 计算树形路径和级次
     *
     * @param entity 实体对象
     */
    public static <T extends AbstractTreeEntity> void calculatePathAndLevel(T entity) {
        AbstractTreeEntity abstractTreeEntity = entity.getParent();
        if (abstractTreeEntity == null) {
            entity.setTreeLevel(1);
            if (entity.getId() != null) {
                entity.setTreePath(entity.getId().toString() + "-");
            } else {
                entity.setTreePath("n/a");
            }
        } else {
            entity.setTreeLevel(abstractTreeEntity.getTreeLevel() + 1);
            if (entity.getId() != null) {
                entity.setTreePath(abstractTreeEntity.getTreePath() + entity.getId().toString() + "-");
            } else {
                entity.setTreePath(abstractTreeEntity.getTreePath());
            }
        }
    }

}
