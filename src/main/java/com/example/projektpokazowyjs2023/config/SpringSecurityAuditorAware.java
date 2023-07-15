package com.example.projektpokazowyjs2023.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

// Klasa służy do śledzenia, kto stworzył lub zmienił podmiot, oraz moment, w którym to się stało.
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }

        User user = (User) auth.getPrincipal();
        return Optional.of(user.getUsername());
    }
}
