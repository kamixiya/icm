package com.kamixiya.icm.core.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Spring的辅助工具类
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    /**
     * 通过名称获取spring容器管理的bean
     *
     * @param name bean名称
     * @return bean
     */
    @SuppressWarnings("unchecked")
    public static <T>T getBean(String name) {
        try {
            return (context == null) ? null : (T)context.getBean(name);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 根据接口Class获取所有实现该接口的Bean实例
     */
    public static <T> T getBeanOfType(Class<T> clzz) {
        return context.getBean(clzz);
    }
    /**
     * 根据接口Class获取所有实现该接口的所有Bean实例
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clzz) {
        return context.getBeansOfType(clzz);
    }
    /**
     * 依赖注入，通常用于向非spring创建的对象中注入依赖对象(注解了@Autowired 或者@Resource的属性）
     *
     * @param obj 需要注入依赖的对象
     */
    public static void injectBeans(Object obj) {

        if (context == null) {
            return;
        }

        Class clazz = obj.getClass();
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                if (f.isAnnotationPresent(Inject.class)) {

                    try {
                        String simpleName = f.getType().getSimpleName();
                        String beanName = StringUtils.uncapitalize(simpleName);

                        Object bean = context.getBean(beanName);
                        if (bean == null) {
                            return;
                        }

                        boolean accessible = f.isAccessible();
                        f.setAccessible(true);
                        f.set(obj, bean);
                        f.setAccessible(accessible);
                    } catch (Exception e) {
                        System.out.println("当对象{" + clazz.getName() + "}注入属性{" + f.getName() +"}的时候，发生了错误: {" + e.getMessage() + "}");
                    }
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (this) {
            SpringUtil.context = applicationContext;
        }
    }
}
