package com.example.projektpokazowyjs2023.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mails2")
@RequiredArgsConstructor
public class MailController2 {

    final private MailService mailService;

    // wyświetlenie formularza
    @GetMapping
    String showForm() {
        return "contact2";
    }

    // formularz wysyłania emaila
    @PostMapping
    String sendMail(@ModelAttribute Mail mail) {
        mailService.sendMail(mail);
        return "redirect:/mails2";
    }
}