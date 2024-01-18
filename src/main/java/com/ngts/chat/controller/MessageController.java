package com.ngts.chat.controller;

import com.ngts.auth.jwt.AuthJwtUtils;
import com.ngts.chat.entity.MessageModelEntity;
import com.ngts.chat.service.ChatUsersService;
import com.ngts.chat.vo.MessageResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    AuthJwtUtils jwtUtils;

    @Autowired
    ChatUsersService chatUsersService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModelEntity message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

       MessageResponseVO messageResponseVOFrom = chatUsersService.findByChatId(message.getFromLogin());
       MessageResponseVO messageResponseVOTo = chatUsersService.findByChatId(to);
       MessageResponseVO responseVO = new MessageResponseVO();
       responseVO.setFromName(messageResponseVOFrom.getFromName());
       responseVO.setMessage(message.getMessage());
       responseVO.setFromLogin(message.getFromLogin());
       responseVO.setToName(messageResponseVOTo.getFromName());
       responseVO.setReceivedTime(String.valueOf(System.currentTimeMillis()));

        System.out.println("handling send message :  From : " + message.getFromLogin() +  " to: " + to + " , Message: " + message + " Session:" + simpMessageHeaderAccessor.getSessionId());
        System.out.println("Session attributes " + simpMessageHeaderAccessor.getSessionAttributes());

        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, responseVO);

        /*UserStorage.getInstance().getUsers().forEach(chatUserEntity -> {
            if (chatUserEntity.getChatId().equalsIgnoreCase(to)) {
                System.out.println("Publishing the message to " + to);

            } else {
                System.out.println(" User not found to publish the message");
            }
        }); */

    }



  }
