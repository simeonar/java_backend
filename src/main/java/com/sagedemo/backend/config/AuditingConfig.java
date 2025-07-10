package com.sagedemo.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import java.util.Optional;

@Configuration
public class AuditingConfig {
    @Bean
    @ConditionalOnMissingBean(name = "auditorProvider")
    public AuditorAware<String> auditorProvider() {
        // TODO: Replace with real user context (e.g., from Spring Security)
        return () -> Optional.of("system");
    }
}
