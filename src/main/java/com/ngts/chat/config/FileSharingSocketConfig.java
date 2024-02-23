package com.ngts.chat.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component
public  class FileSharingSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
       // registry.addHandler(new MyTextHandler(), "/text").withSockJS();
        registry.addHandler(new MyBinaryHandler(), "/binary").withSockJS();
    }
}