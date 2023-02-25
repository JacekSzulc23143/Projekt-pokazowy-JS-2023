package com.example.projektpokazowyjs2023.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String index() { // Akcja obsługująca login
        return "security/login";
    }
}
