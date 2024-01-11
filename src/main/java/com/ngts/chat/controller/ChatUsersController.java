package com.ngts.chat.controller;

import com.ngts.chat.entity.ChatUserEntity;
import com.ngts.chat.entity.EOnlineStatus;
import com.ngts.chat.repository.UserStorage;
import com.ngts.chat.service.ChatUsersService;
import com.ngts.chat.vo.ChatUserVO;
import com.ngts.chat.vo.UserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/comm")
public class ChatUsersController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatUsersService chatUsersService;
/*
    @GetMapping("/registration/{userName}")
    public ResponseEntity<Void> register(@PathVariable String userName) {
        System.out.println("handling register user request: " + userName);
        try {
            UserStorage.getInstance().setUser(userName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        simpMessagingTemplate.convertAndSend("/users" , userName);
        return ResponseEntity.ok().build();
    }*/

    @PostMapping("/chat/login")
    public ResponseEntity<?> login(@Payload ChatUserVO chatUser) {
        System.out.println("handling register user request: " + chatUser.getEmail());
        UUID uuid = UUID.randomUUID();
        String uniqueUserId = uuid.toString();
        try {
            ChatUserEntity chatUserEntity = new ChatUserEntity();
            chatUserEntity.setChatId(uniqueUserId);
            chatUserEntity.setUsername(chatUser.getUsername());
            chatUserEntity.setEmail(chatUser.getEmail());
            chatUserEntity.setStatus(EOnlineStatus.ONLINE);
            UserStorage.getInstance().setUser(chatUserEntity);
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            return ResponseEntity.badRequest().body(errorMsg);
        }
/*
        ChatUserEntity responseUserObj = chatUsersService.saveUser(chatUser); */

        UserResponseVO userResponseVO = new UserResponseVO();
        userResponseVO.setChatUserId(uniqueUserId);
        userResponseVO.setUsername(chatUser.getUsername());

        simpMessagingTemplate.convertAndSend("/users" , userResponseVO);

        System.out.println("Session Id generated for the user " + userResponseVO.getChatUserId());

        return ResponseEntity.ok().body(userResponseVO);
    }

    @GetMapping("/fetchAllUsers")
    public Set<ChatUserEntity> fetchAll() {
           return UserStorage.getInstance().getUsers();
    }
}
