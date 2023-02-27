package com.example.projektpokazowyjs2023;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

//  Po wejściu na http://localhost:8080/ zostajemy przekierowani do strony logowania:
    @GetMapping("/")
    public String index() { // // index domyślna strona "Bug Tracker - Projekt pokazowy - Studia podyplomowe - WSB - 2022/2023"
        return "index";
    }

    @GetMapping("/contact")
    public String contact() { // Strona kontaktowa "To ja Jacek student WSB"
        return "contact";
    }

    @GetMapping("/contact2")
    public String contact2() { // Strona kontaktowa, dostępna dla wszystkich "To ja Jacek student WSB"
        return "contact2";
    }
}
