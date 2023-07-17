package com.example.projektpokazowyjs2023.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/contact2", "/mails2").permitAll() //1. Strona /contact2 będzie dostępna dla
                    // niezalogowanych
                    .requestMatchers("/images/**").permitAll()//2. przepuszcza obrazki
                    .requestMatchers("/js/**").permitAll()//3. przepuszcza pliki JavaScript
                    .anyRequest().authenticated() //4 Wszystkie pozostałe adresy będą zabezpieczone
            )
            .formLogin((form) -> form
                    .loginPage("/login")// 5. Aplikacja będzie wyświetlała przygotowaną stronę, zamiast
                    // tej domyślnej oferowanej przez Spring Security
                    .permitAll()
            )
            .csrf().ignoringRequestMatchers("/issues/state/**", "/issues/priority/**", "/issues/type/**")
            // wykluczenie potrzebne dla AJAX
            .and()
            .logout((logout) -> logout.permitAll());

        return http.build();
    }

    // haszowanie hasła => PersonService
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Serwis odpowiedzialny za odczyt użytkownika z bazy danych.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        // Zdefiniowana metoda hashowania
        provider.setPasswordEncoder(bCryptPasswordEncoder());

        // Serwis odpowiedzialny za użytkowników
        provider.setUserDetailsService(customUserDetailsService);

        return provider;
    }

    // Informacje o bieżącym twórcy. AuditorAware należy zaimplementować, aby poinformować infrastrukturę, kim jest
    // bieżący użytkownik.
    @Bean
    AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}