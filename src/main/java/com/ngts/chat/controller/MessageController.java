package com.ngts.chat.controller;

import com.ngts.chat.entity.MessageModelEntity;
import com.ngts.chat.repository.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModelEntity message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        System.out.println("handling send message: " + message + " to: " + to);
        //System.out.println("Session ID " + simpMessageHeaderAccessor.getSessionId());
        //System.out.println("Session ID ---->  " + simpMessageHeaderAccessor.getSessionAttributes());
        System.out.println("" + message.getFromLogin());
        UserStorage.getInstance().getUsers().forEach(chatUserEntity -> {
            if (chatUserEntity.getChatId().equalsIgnoreCase(to)) {
                System.out.println("Publishing the message to " + to);
                simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
            } else {
                System.out.println(" User not found to publish the message");
            }
        });

    }

  }
