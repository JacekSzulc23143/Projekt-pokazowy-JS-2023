package com.example.projektpokazowyjs2023.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    final private JavaMailSender javaMailSender;

    // metoda wysyłająca emeila
    void sendMail(Mail mail) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setTo("jacekpa23143@gmail.com");
            mimeMessageHelper.setSubject(mail.subject);
            mimeMessageHelper.setText(mail.content);
            mimeMessageHelper.addAttachment(mail.attachment.getOriginalFilename(), mail.attachment); // dodanie
            // załącznika do meila

            javaMailSender.send(mimeMessage);
            System.out.println("Wysłanie emaila powiodło się!");
        } catch (Exception e) {
            System.out.println("Wysyłanie mejla nie powiodło się!");
        }
    }
}