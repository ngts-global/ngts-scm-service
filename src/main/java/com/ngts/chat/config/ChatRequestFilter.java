package com.ngts.chat.config;

import com.ngts.chat.utils.AuthUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import java.util.Map;


@Component
public class ChatRequestFilter implements HandshakeInterceptor {

    public static String className = ChatRequestFilter.class.getName();

    AuthUtils authUtils = new AuthUtils();

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println(className + " Before handshake & This is where we need to check all request header validation for chat");

         if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                HttpSession session = servletRequest.getServletRequest().getSession(true);
                if (session != null) {
                    authUtils.getRequestCookies(servletRequest);
                    System.out.println(className + " Uri " + request.getURI().getPath());
                    System.out.println(className + " Setting session id in object " + session.getId());
                    System.out.println(className + " Last accessed time " + session.getLastAccessedTime());
                    System.out.println(className + " Session timeout " + session.getMaxInactiveInterval());
                    attributes.put("SESSION_ID", session.getId());


                }else {
                    System.out.println(className + " Session is null");
             }
            }else {
             System.out.println(className + " Filter .......");
             System.out.println(className + " Uri " +  request.getURI().getPath() );
         }
           return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println(className + " After handshake this logic to be executed");

    }


}
