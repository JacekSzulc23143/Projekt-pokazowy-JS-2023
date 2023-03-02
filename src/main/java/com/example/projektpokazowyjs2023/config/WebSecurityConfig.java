package com.example.projektpokazowyjs2023.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/contact2").permitAll() //1. Strona /contact2 będzie dostępna dla
                        // niezalogowanych
                        .requestMatchers("/images/**").permitAll()//2. przepuszcza obrazki
                        .anyRequest().authenticated() //3. Wszystkie pozostałe adresy będą zabezpieczone
                )
                .formLogin((form) -> form
                        .loginPage("/login")// 4. Aplikacja będzie wyświetlała przygotowaną stronę, zamiast
                        // tej domyślnej oferowanej przez Spring Security
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    // Opcja przygotowania demonstracyjnego użytkownika
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
