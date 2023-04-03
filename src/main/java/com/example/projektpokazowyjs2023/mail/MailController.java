package com.example.projektpokazowyjs2023.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mails")
@RequiredArgsConstructor
public class MailController {

    final private MailService mailService;

    // wyświetlenie formularza
    @GetMapping
    String showForm() {
        return "contact";
    }

    // formularz wysyłania emaila
    @PostMapping
    String sendMail(@ModelAttribute Mail mail, RedirectAttributes redirectAttrs) {
        mailService.sendMail(mail);
        redirectAttrs.addFlashAttribute("status", "success");
        return "redirect:/mails";
    }
}