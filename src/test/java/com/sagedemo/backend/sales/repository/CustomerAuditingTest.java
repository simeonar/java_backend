package com.sagedemo.backend.sales.repository;

import com.sagedemo.backend.sales.entity.Customer;
import com.sagedemo.backend.config.AuditingConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({com.sagedemo.backend.config.JpaAuditingEnabler.class, CustomerAuditingTest.TestAuditorAwareConfig.class})
class CustomerAuditingTest {
    @TestConfiguration
    static class TestAuditorAwareConfig {
        @Bean
        public AuditorAware<String> auditorProvider() {
            return () -> Optional.of("test-user");
        }
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void createdByAndUpdatedBy_areSet() {
        Customer customer = new Customer();
        customer.setCompanyName("Test Customer");
        Customer saved = customerRepository.save(customer);
        assertThat(saved.getCreatedBy()).isEqualTo("test-user");
        assertThat(saved.getUpdatedBy()).isEqualTo("test-user");
    }
}