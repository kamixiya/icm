package com.kamixiya.icm.service.common.exception;

/**
 * 存在相同的值时抛出异常
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
public class NoPositionDataException extends RuntimeException {

    public NoPositionDataException() {
        super("必须设置岗位数据!");
    }
}