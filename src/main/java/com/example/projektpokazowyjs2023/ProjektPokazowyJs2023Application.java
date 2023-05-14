package com.example.projektpokazowyjs2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ProjektPokazowyJs2023Application {

    public static void main(String[] args) {
        SpringApplication.run(ProjektPokazowyJs2023Application.class, args);
    }

}
