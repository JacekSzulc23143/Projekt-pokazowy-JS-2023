package com.example.projektpokazowyjs2023.mail;

import com.example.projektpokazowyjs2023.issues.Issue;
import com.example.projektpokazowyjs2023.people.Person;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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

            mimeMessageHelper.setTo("jacekpa23143@gmail.com");
            mimeMessageHelper.setSubject(mail.subject);
            mimeMessageHelper.setText("Wiadomość od: " + mail.recipient +"\r\n"+ mail.content);
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
    public void sendToContractor(Issue issue) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setTo(issue.getContractor().getEmail());
            mimeMessageHelper.setSubject("/BUG TRACKER/ - zgłoszenie o nazwie " + "\"" + issue.getName() + "\"");
            mimeMessageHelper.setText("Dzień dobry," +"\r\n"+ "zostało przydzielone do Ciebie zgłoszenie, dotyczy: " + issue.getDescription() +"\r\n"+
                    "Więcej informacji dostępnych jest w aplikacji \"Bug Tracker\"." +"\r\n"+ "Prosimy nie odpowiadać" +
                    " na tę wiadomość.");

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

            mimeMessageHelper.setTo(issue.getCreator().getEmail());
            mimeMessageHelper.setSubject("/BUG TRACKER/ - zgłoszenie o nazwie " + "\"" + issue.getName() + "\"");
            mimeMessageHelper.setText("Dzień dobry," +"\r\n"+ "zostało wykonane zgłoszenie, dotyczy: " + issue.getDescription() +"\r\n"+
                    "Więcej informacji dostępnych jest w aplikacji \"Bug Tracker\"." +"\r\n"+ "Prosimy nie odpowiadać" +
                    " na tę wiadomość.");

            javaMailSender.send(mimeMessage);
            System.out.println("Wysłanie E-maila powiodło się!");
        } catch (Exception e) {
            System.out.println("Wysłanie E-maila nie powiodło się! " + e);
        }
    }
}