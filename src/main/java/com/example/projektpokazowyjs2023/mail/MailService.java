package com.example.projektpokazowyjs2023.mail;

import com.example.projektpokazowyjs2023.issues.Issue;
import com.example.projektpokazowyjs2023.people.Person;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class MailService {

    final private JavaMailSender javaMailSender;
    public Issue issue;
    public Person person;

    // metoda wysyłająca E-maila
    void sendMail(Mail mail) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

/*
            https://www.baeldung.com/java-localization-messages-formatting
            https://www.baeldung.com/java-8-localization
*/

            Locale defaultLocale = Locale.getDefault();
            ResourceBundle bundle = ResourceBundle.getBundle("languages/mail", defaultLocale);

            String subject0 = bundle.getString("subject0");
            String content0 = bundle.getString("content0");

            mimeMessageHelper.setTo("jacekpa23143@gmail.com");
            mimeMessageHelper.setSubject(subject0 + " " + "- " + mail.subject + " -");
            mimeMessageHelper.setText(content0 + "\n-----\n" + mail.content + "\n-----");
            mimeMessageHelper.addAttachment(mail.attachment.getOriginalFilename(), mail.attachment); // dodanie
            // załącznika do meila

            javaMailSender.send(mimeMessage);
            System.out.println("Wysłanie E-maila powiodło się!");
        } catch (Exception e) {
            System.out.println("Wysłanie E-maila nie powiodło się! " + e);
        }
    }

    // metoda wysyłająca E-maila do osoby przypisanej do zgłoszenia, który jest wysyłany w momencie przydzielenia jej
    // jakiegoś zadania.
    public void sendToAssignee(Issue issue) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            Locale defaultLocale = Locale.getDefault();
            ResourceBundle bundle = ResourceBundle.getBundle("languages/mail", defaultLocale);

            String subject1 = bundle.getString("subject1");
            String content1 = bundle.getString("content1");

            String subjectFormat1 = MessageFormat.format(subject1, issue.getName());
            String contentFormat1 = MessageFormat.format(content1, issue.getDescription());

            mimeMessageHelper.setTo(issue.getAssignee().getEmail());
            mimeMessageHelper.setSubject(subjectFormat1);
            mimeMessageHelper.setText(contentFormat1);

            javaMailSender.send(mimeMessage);
            System.out.println("Wysłanie E-maila powiodło się!");
        } catch (Exception e) {
            System.out.println("Wysłanie E-maila nie powiodło się! " + e);
        }
    }

    // metoda wysyłająca E-maila do twórcy zgłoszenia, który jest wysyłany w momencie wykonania zgłoszenia.
    public void sendToCreator(Issue issue) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            Locale defaultLocale = Locale.getDefault();
            ResourceBundle bundle = ResourceBundle.getBundle("languages/mail", defaultLocale);

            String subject2 = bundle.getString("subject2");
            String content2 = bundle.getString("content2");

            String subjectFormat2 = MessageFormat.format(subject2, issue.getName());
            String contentFormat2 = MessageFormat.format(content2, issue.getDescription());

            mimeMessageHelper.setTo(issue.getCreator().getEmail());
            mimeMessageHelper.setSubject(subjectFormat2);
            mimeMessageHelper.setText(contentFormat2);

            javaMailSender.send(mimeMessage);
            System.out.println("Wysłanie E-maila powiodło się!");
        } catch (Exception e) {
            System.out.println("Wysłanie E-maila nie powiodło się! " + e);
        }
    }
}