package com.kamixiya.icm.service.common.exception;

/**
 * InvalidPasswordException
 *
 * @author Zhu Jie
 * @date 2020/3/30
 */
public class InvalidPasswordException extends RuntimeException  {

    public InvalidPasswordException(String message) {
        super(message);
    }
}
