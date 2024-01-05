package com.ngts.chat1.controller;

import com.ngts.chat1.entity.ChatUser;
import com.ngts.chat1.repository.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin
public class UsersController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

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
    }

    @PostMapping("/chat/login")
    public ResponseEntity<String> login(@Payload ChatUser chatUser) {
        System.out.println("handling register user request: " + chatUser.getEmail());
        try {
            UserStorage.getInstance().setUser(chatUser.getUsername());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        simpMessagingTemplate.convertAndSend("/users" , chatUser.getUsername());

        UUID uuid = UUID.randomUUID();
        String uniqueUserId = uuid.toString();

        System.out.println("Session Id generated for the user " + uniqueUserId);

        return ResponseEntity.ok().body(uniqueUserId);
    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        return UserStorage.getInstance().getUsers();
    }
}
