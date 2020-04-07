package com.kamixiya.icm.core.json;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * DynamicFilterMixIn
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@JsonFilter(DynamicFilterProvider.FILTER_ID)
public class DynamicFilterMixIn {
}
