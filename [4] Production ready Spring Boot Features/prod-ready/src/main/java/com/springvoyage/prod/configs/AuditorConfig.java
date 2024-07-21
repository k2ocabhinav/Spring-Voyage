package com.springvoyage.prod.configs;

import com.springvoyage.prod.auth.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditorConfig {

    @Bean
    AuditorAware<String> getAuditorAwareImpl(){
        return new AuditorAwareImpl();
    }
}
