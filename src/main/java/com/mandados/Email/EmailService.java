package com.mandados.Email;


import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String body, String topic){
        System.out.println("Sending message");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // String contacto = "contacto";
        simpleMailMessage.setFrom("no-reply@mandados.com");// quien envía
        // simpleMailMessage.setBcc("lilo2017c@gmail.com");//segundo email a enviar
        // simpleMailMessage.setCc("lilo-cirilo@hotmail.com");//
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
        System.out.println("Send message...");
    }
	
	public void sendEmailMime(String to, String body, String topic){
        System.out.println("Sending message");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper simpleMailMessage = new MimeMessageHelper(message, true);
            
            simpleMailMessage.setFrom("no-reply@mandados.com");// quien envía
            // simpleMailMessage.setBcc("lilo2017c@gmail.com");//segundo email a enviar
            simpleMailMessage.setCc("lilo-cirilo@hotmail.com");//
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(topic);
            // simpleMailMessage.setText(body);
            simpleMailMessage.setText("my text <img src='http://crud-persona.herokuapp.com/logo.png'>", true);
            simpleMailMessage.addInline("logo3", new ClassPathResource("logo3.png"));
            simpleMailMessage.addAttachment("logo-mama.png", new ClassPathResource("logo-mama.png"));
            FileSystemResource file = new FileSystemResource(new File("logo.png"));
            simpleMailMessage.addAttachment("Invoice", file);
            
            javaMailSender.send(message);
        } catch (Exception e) {
        }
        
        System.out.println("Send message...");
    }
}