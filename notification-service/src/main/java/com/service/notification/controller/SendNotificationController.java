package com.service.notification.controller;

import com.service.notification.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SendNotificationController {

    @Value("${server.port}")
    private int port;

    private final MailService mailService;

    @KafkaListener(topics = "${spring.kafka.topic.mail}", groupId = "${spring.kafka.consumer.group.group-id}")
    public void listen(String message) {
        System.out.println("Load balancer in port: " + port);
        System.out.println("Received Message in group - group-id: " + message);
        mailService.sendActiveUserMail(message);
    }
}