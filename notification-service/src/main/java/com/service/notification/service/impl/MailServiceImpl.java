package com.service.notification.service.impl;

import com.service.notification.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendActiveUserMail(String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(email);
            message.setSubject("Account create!");
            message.setText("Hey! You just create your account");
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Can't send mail to " + email,e);
        }
    }
}
