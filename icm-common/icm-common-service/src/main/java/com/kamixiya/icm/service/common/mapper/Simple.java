package com.kamixiya.icm.service.common.mapper;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  WithParent
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Qualifier
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Simple {
}
