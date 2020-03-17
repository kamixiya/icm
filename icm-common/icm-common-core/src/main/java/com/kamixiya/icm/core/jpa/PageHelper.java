package com.kamixiya.icm.core.jpa;

import com.kamixiya.icm.model.common.PageDataDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用于生成JPA的分页请求参数
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
public class PageHelper {

    private static final int ONE_FILED_NO = 2;

    private PageHelper() {
    }

    /**
     * 将字符串信息转换为排序信息
     *
     * @param sortFields 排序字符串
     * @return 排序信息
     */
    public static List<Sort.Order> toSortOrderList(String sortFields) {

        List<Sort.Order> orders = new ArrayList<>();

        if (StringUtils.isEmpty(sortFields)) {
            return orders;
        }

        String[] fields = sortFields.replace(" ", "").split(",");
        if (fields.length % ONE_FILED_NO != 0) {
            return orders;
        }

        String fieldName;
        String sortDirection;
        for (int i = 0; i < fields.length / ONE_FILED_NO; i++) {
            fieldName = fields[i * ONE_FILED_NO];
            sortDirection = fields[i * ONE_FILED_NO + 1];
            if ("asc".equalsIgnoreCase(sortDirection)) {
                orders.add(new Sort.Order(Sort.Direction.ASC, fieldName));
            } else if ("desc".equalsIgnoreCase(sortDirection)) {
                orders.add(new Sort.Order(Sort.Direction.DESC, fieldName));
            }
        }

        return orders;
    }

    /**
     * 生成分页请求
     *
     * @param pageNo     页号，从0开始计数
     * @param pageSize   每页数量
     * @param sortFields 排序字符串，使用逗号分隔字段，asc和desc指明排序方向。 例如：字段1,asc,字段2,desc
     * @return 分页请求
     */
    public static PageRequest generatePageRequest(int pageNo, int pageSize, String sortFields) {
        PageRequest pr = null;
        if (StringUtils.isNoneBlank(sortFields)) {
            List<Sort.Order> orders = toSortOrderList(sortFields);
            if (!orders.isEmpty()) {
                pr = PageRequest.of(pageNo, pageSize, Sort.by(orders));
            }
        }
        if (pr == null) {
            pr = PageRequest.of(pageNo, pageSize);
        }
        return pr;
    }

    /**
     * 将JPA分页数据转成DTO
     *
     * @param <T>  Entity
     * @param <K>  DTO
     * @param page JPA分页数据
     * @param data 已转换的DTO
     * @return 分页DTO数据
     */
    public static <T, K> PageDataDTO<K> toPageData(Page<T> page, Collection<K> data) {
        return new PageDataDTO<>(page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), data);
    }
}
