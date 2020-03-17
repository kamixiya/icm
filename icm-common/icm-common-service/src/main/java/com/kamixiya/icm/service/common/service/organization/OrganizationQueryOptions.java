package com.kamixiya.icm.service.common.service.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrganizationQueryOptions
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationQueryOptions {
    /**
     * 组织id
     */
    String organizationId;
    /**
     * 是否读取所有子组织员工
     */
    private Boolean deepLoad;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 员工账号
     */
    private String account;

    /**
     * 员工状态
     */
    private Boolean available;

}
