package com.kamixiya.icm.service.common.exception;

/**
 * 员工设置的默认岗位不存在所传的组织集合里
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
public class DefaultOrganizationIdNoExistsPositionDataException extends RuntimeException {

    public DefaultOrganizationIdNoExistsPositionDataException() {
        super("默认岗位不存在所传的组织集合中！");
    }
}