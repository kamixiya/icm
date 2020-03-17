package com.kamixiya.icm.service.common.exception;

/**
 * Token已经失效
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
public class InvalidTokenException extends RuntimeException {

    private static final String MSG = "无效的Token，请重新登录！";

    public InvalidTokenException() {
        super(MSG);
    }
}
