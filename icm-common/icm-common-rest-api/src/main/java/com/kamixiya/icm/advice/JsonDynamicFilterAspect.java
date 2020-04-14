package com.kamixiya.icm.advice;

import com.kamixiya.icm.core.json.CustomJsonSerializer;
import com.kamixiya.icm.core.json.DynamicFilterProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * JsonDynamicFilterAspect
 *
 * @author Zhu Jie
 * @date 2020/4/14
 */
@Order(1)
@Aspect
@Component
public class JsonDynamicFilterAspect {

    @Pointcut("@annotation(com.kamixiya.icm.core.json.Json)")
    public void jsonAspect() {
        // 定义切面，没有具体内容
    }

    @Pointcut("@annotation(com.kamixiya.icm.core.json.Jsons)")
    public void jsonsAspect() {
        // 定义切面，没有具体内容
    }

    @Around("jsonAspect()")
    public Object doServiceAround1(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return doService(proceedingJoinPoint);
    }

    @Around("jsonsAspect()")
    public Object doServiceAround2(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return doService(proceedingJoinPoint);
    }

    private Object doService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 设置json
        CustomJsonSerializer cjs = new CustomJsonSerializer();
        cjs.setFilters((MethodSignature) proceedingJoinPoint.getSignature());
        DynamicFilterProvider.setSerializer(cjs);

        return proceedingJoinPoint.proceed();
    }
}
