package com.example.monitoringandcommunicationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/sendNotification")
    public void sendNotification(String message) throws Exception{
        simpMessagingTemplate.convertAndSend("/sendNotification","Notification: " + message);
    }

}
