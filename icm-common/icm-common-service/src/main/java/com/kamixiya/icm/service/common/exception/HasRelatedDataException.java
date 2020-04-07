package com.kamixiya.icm.service.common.exception;

/**
 * HasRelatedDataException
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
public class HasRelatedDataException extends RuntimeException {
    public HasRelatedDataException(String id) {
        super(String.format("存在关联数据(%s)！", id));
    }
}