package com.ngts.chat.controller;

import com.ngts.auth.jwt.AuthJwtUtils;
import com.ngts.chat.service.ChatUsersService;
import com.ngts.chat.service.MessageService;
import com.ngts.chat.utils.CommonUtils;
import com.ngts.chat.vo.req.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {

    public static String className = MessageController.class.getName();
    @Autowired
    AuthJwtUtils jwtUtils;

    @Autowired
    ChatUsersService chatUsersService;

    @Autowired
    MessageService messageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageVO message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

        if(message.getFromId().equalsIgnoreCase(to)){
            log.error(className + " trying to send msg to same person. Not supported now. ");
        }else {
            MessageVO messageResponseVOFrom = chatUsersService.findByChatId(message.getFromId());
            MessageVO messageResponseVOTo = chatUsersService.findByChatId(to);
            MessageVO message1 = new MessageVO();
            message1.setFromName(messageResponseVOFrom.getFromName());
            message1.setMsgId(CommonUtils.generateUID());
            message1.setFromId(messageResponseVOFrom.getFromId());
            message1.setMsgType("txt");
            message1.setToId(messageResponseVOTo.getFromId());
            message1.setToName(messageResponseVOTo.getFromName());
            message1.setTime(String.valueOf(System.currentTimeMillis()));
            message1.setStatus("S");
            message1.setMsgTxt(message.getMsgTxt());

            System.out.println("Sending message one to one ...");;
            System.out.println(className + " handling send message :  "+ " , Message: " + message1.toString() + " Session ID: " + simpMessageHeaderAccessor.getSessionId());
            System.out.println(className + " Destination  " + simpMessageHeaderAccessor.getDestination());

            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message1);

            messageService.saveMsg(message1);
        }
    }

    @MessageMapping("/channels/{from}/{channelId}")
    public void sendGroupMessage(@DestinationVariable String from , @DestinationVariable String channelId,
                                 MessageVO message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        System.out.println(className + " Sending messages to group .....");
        System.out.println(className + " handling send message :  From : "
                 + from + " , Channel ID : " + channelId + " , Session ID: " + simpMessageHeaderAccessor.getSessionId());
        System.out.println(className + " Destination  " + simpMessageHeaderAccessor.getDestination());
        simpMessagingTemplate.convertAndSend("/channels/"+from+"/"+channelId , "responseVO");
    }

    private MessageVO createMessageObject(){
        return null;
    }

  }
