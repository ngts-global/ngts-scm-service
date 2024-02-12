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

    public static String className = MessageController.class.getName();
    @Autowired
    AuthJwtUtils jwtUtils;

    @Autowired
    ChatUsersService chatUsersService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to,
                            MessageModelEntity message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

       MessageResponseVO messageResponseVOFrom = chatUsersService.findByChatId(message.getFromLogin());
       MessageResponseVO messageResponseVOTo = chatUsersService.findByChatId(to);
       MessageResponseVO responseVO = new MessageResponseVO();
       responseVO.setFromName(messageResponseVOFrom.getFromName());
       responseVO.setMessage(message.getMessage());
       responseVO.setFromLogin(message.getFromLogin());
       responseVO.setToName(messageResponseVOTo.getFromName());
       responseVO.setReceivedTime(String.valueOf(System.currentTimeMillis()));
       responseVO.setMsgId(System.currentTimeMillis());
        System.out.println("Sending message one to one ...");;
       System.out.println(className + " handling send message :  From : " + message.getFromLogin() +  " to: " + to + " , Message: " + message + " Session ID: " + simpMessageHeaderAccessor.getSessionId());
       System.out.println(className + " Destination  " + simpMessageHeaderAccessor.getDestination());

       //System.out.println(className +" subId " +  simpMessageHeaderAccessor.getSubscriptionId());

        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, responseVO);

    }

    @MessageMapping("/channels/{from}/{channelId}")
    public void sendGroupMessage(@DestinationVariable String from ,@DestinationVariable String channelId,
                            MessageModelEntity message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        System.out.println(className + " Sending messages to group .....");
        System.out.println(className + " handling send message :  From : "
                 + from + " , Channel ID : " + channelId + " , Session ID: " + simpMessageHeaderAccessor.getSessionId());
        System.out.println(className + " Destination  " + simpMessageHeaderAccessor.getDestination());

    }

  }
