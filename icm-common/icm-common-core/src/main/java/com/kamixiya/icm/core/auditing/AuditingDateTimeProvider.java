package com.kamixiya.icm.core.auditing;

import org.springframework.data.auditing.DateTimeProvider;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

/**
 * 提供当前时间，用于auditing
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
public class AuditingDateTimeProvider implements DateTimeProvider {

    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(LocalDateTime.now());
    }
}

