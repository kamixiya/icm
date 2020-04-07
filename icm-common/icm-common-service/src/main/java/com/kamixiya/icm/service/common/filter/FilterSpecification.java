package com.kamixiya.icm.service.common.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * FilterSpecification
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Setter
@Getter
public class FilterSpecification {
    /**
     * 过滤器名称
     */
    private String filterName;
    /**
     * 过滤器参数键值
     */
    private Map<String, Object> parameters;

}
