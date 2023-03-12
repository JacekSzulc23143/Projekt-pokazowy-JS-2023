package com.example.projektpokazowyjs2023.mail;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class Mail {

    String recipient;

    String subject;

    String content;

    MultipartFile attachment;

}