package com.kamixiya.icm.service.common.exception;

/**
 * 上传文件格式错误，返回该异常
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
public class FileFormatErrorException extends Exception {
    private static final String ERROR_MSG = "上传的文件格式错误！";

    public FileFormatErrorException() {
        super(ERROR_MSG);
    }
}
