package com.company.storeemail.rest.service;

import com.company.storeemail.rest.model.RequestEmail;
import com.company.storeemail.rest.port.EmailPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender sender;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }


    @Override
    public boolean sendEmail(RequestEmail requestEmail) {
        return sendEmailTool(requestEmail.getContent(),requestEmail.getEmail(),requestEmail.getSubject());
    }

   private boolean sendEmailTool(String content, String email, String subject){
        boolean send = false;

       MimeMessage message = sender.createMimeMessage();
       MimeMessageHelper helper = new MimeMessageHelper(message);
       try {
           helper.setTo(email);
           helper.setText(content,true);
           helper.setSubject(subject);
           sender.send(message);
           send = true;
           LOGGER.info("Mail enviado!");

       }catch (MessagingException e) {
           LOGGER.error("Hubo un error al enviar el mail: {}", e);
       }

       return send;
   }
}
