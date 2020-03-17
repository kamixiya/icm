package com.kamixiya.icm.core.idgenerator;

/**
 * TimeBackException
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
public class ClockBackException extends RuntimeException {

    public ClockBackException() {
        super("Clock moved backwards.  Refusing to generate id!");
    }
}
