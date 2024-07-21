package com.springvoyage.prod.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        /*
        * Get info regarding the auditor
        * Get authentication
        * Get the principle
        * Get the username
        */
        return Optional.of("Abhinav");
    }
}
