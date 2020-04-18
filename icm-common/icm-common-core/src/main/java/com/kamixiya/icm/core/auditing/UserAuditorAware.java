package com.kamixiya.icm.core.auditing;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 提供当前登录用户名，用于auditing
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
public class UserAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }


        if (authentication.getPrincipal() == null) {
            return Optional.empty();
        }

        try {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(authentication.getPrincipal().getClass(), "userId");
            if (propertyDescriptor != null) {
                Method method = propertyDescriptor.getReadMethod();
                if (method != null) {
                    Object value = method.invoke(authentication.getPrincipal());
                    return Optional.of(Long.valueOf(value.toString()));
                }
            }

        } catch (Exception e) {
            // nothing to do
        }

        return Optional.empty();

    }
}
