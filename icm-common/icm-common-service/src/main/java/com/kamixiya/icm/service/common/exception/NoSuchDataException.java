package com.kamixiya.icm.service.common.exception;

import java.io.Serializable;

/**
 * NoSuchDataException
 *
 * @author Zhu Jie
 * @date 2020/3/12
 */
public class NoSuchDataException extends RuntimeException {

    public NoSuchDataException(Serializable id) {
        super(String.format("没有找到对应的数据(%s)！", id));
    }
}
