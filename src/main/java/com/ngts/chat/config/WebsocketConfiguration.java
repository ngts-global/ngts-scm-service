package com.ngts.chat.config;

import com.ngts.auth.jwt.AuthJwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

    public static String className = WebsocketConfiguration.class.getName();

    @Autowired
    AuthJwtUtils jwtUtils;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/chat")
                .addInterceptors(new ChatRequestFilter())
                //.setAllowedOrigins("http://localhost:9090","http://vmi240110.contaboserver.net:7070/")
                .setAllowedOriginPatterns("*") // This has to be corrected when we deploy it in to production
                .withSockJS();
        registry.addEndpoint("/fluchat")
                .addInterceptors(new ChatRequestFilter())
                //.setAllowedOrigins("http://localhost:9090","http://vmi240110.contaboserver.net:7070/")
                .setAllowedOriginPatterns("*"); // This has to be corrected when we deploy it in to production

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
                registry.setApplicationDestinationPrefixes("/app")
                        .enableSimpleBroker("/topic", "/users", "/channel");
    }

    @Component
    public static class MyTextHandler extends TextWebSocketHandler {
        public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
           // session.sendMessage(new TextMessage("hello world"));
            System.out.println(className + " Text handler ........");
        }
    }

    @Component
    public static class MyBinaryHandler extends BinaryWebSocketHandler {
        public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
            try {
                //session.sendMessage(new BinaryMessage("hello world".getBytes()));
                System.out.println(className + " Binary handler ........");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
