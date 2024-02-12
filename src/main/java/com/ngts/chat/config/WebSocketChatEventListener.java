package com.ngts.chat.config;

import com.ngts.auth.jwt.AuthJwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketChatEventListener {

    public static String className = WebSocketChatEventListener.class.getName();

    @Autowired
    AuthJwtUtils jwtUtils;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        System.out.println(className + " Received a new web socket connection with SessionId " + event.getMessage().getHeaders().get("simpSessionId"));
      /*  System.out.println(className + " attributes " + event.getMessage().getHeaders().values());
        // not working System.out.println(className + " simpSessionAttributes -- > " +event.getMessage().getHeaders().get("simpSessionAttributes"));
        event.getMessage().getHeaders().values().forEach(o -> {
            System.out.println(className + " values ......>  " + o  + " Type " + o.getClass().getName());
        });
        event.getMessage().getHeaders().entrySet().forEach(stringObjectEntry -> {
            System.out.println(className + " Entry .. " + stringObjectEntry);
        });*/

        GenericMessage genericMessage  = (GenericMessage) event.getMessage().getHeaders().get("simpConnectMessage");
        System.out.println(className + " simpleSession Attribute : " +  genericMessage.getHeaders().get("simpSessionAttributes"));
       /* genericMessage.getHeaders().entrySet().forEach(stringObjectEntry -> {
            System.out.println(className + " values1111 ......>  " + stringObjectEntry  + " Type " + stringObjectEntry.getClass().getName());

        });*/
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println(className + " Disconnected the SessionId " + event.getMessage().getHeaders().get("simpSessionId"));

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
           //  messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}