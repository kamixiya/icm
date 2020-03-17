package com.kamixiya.icm.core.rest;


import java.lang.annotation.*;


/**
 * 标识非Spring自动注入的属性
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {
}
