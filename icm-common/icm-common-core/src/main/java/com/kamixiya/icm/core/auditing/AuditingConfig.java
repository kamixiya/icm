package com.kamixiya.icm.core.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Auditing 配置
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "dateTimeProvider")
public class AuditingConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new UserAuditorAware();
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return new AuditingDateTimeProvider();
    }
}

