package com.kamixiya.icm.service.common.utils;

import com.kamixiya.icm.model.common.PageDataDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;

/**
 * PageDataUtil
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
public class PageDataUtil {

    private PageDataUtil() {
    }

    /**
     * 将JPA分页数据转成DTO
     *
     * @param page JPA分页数据
     * @param data 已转换的DTO
     * @param <T>  Entity
     * @param <K>  DTO
     * @return 分页DTO数据
     */
    public static <T, K> PageDataDTO<K> toPageData(Page<T> page, Collection<K> data) {
        return new PageDataDTO<>(page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), data);
    }
}
