package com.ngts.chat.controller;

import com.ngts.auth.jwt.AuthJwtUtils;
import com.ngts.auth.payload.response.MessageResponse;
import com.ngts.chat.service.ChatUsersService;
import com.ngts.chat.vo.ChatRegisterationVO;
import com.ngts.chat.vo.ChatUserVO;
import com.ngts.chat.vo.UserResponseVO;
import com.ngts.common.constants.NgtsCommonConstants;
import com.ngts.common.redis.RedisCacheUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/comm/chat")
public class ChatUsersController {

    @Autowired
    AuthJwtUtils jwtUtils;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Value("${ngtsscm.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatUsersService chatUsersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Payload ChatUserVO chatUser) {
        System.out.println("handling register user request: " + chatUser.getEmail());
        UserResponseVO responseUserObj = chatUsersService.findAllUsersByEmailAndPassword(chatUser.getEmail(), chatUser.getPassword());
        if(responseUserObj == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Bad credentials !"));
        }
        ResponseCookie jwtCookie = null;
        try{
            jwtCookie = jwtUtils.generateJwtCookie(chatUser.getEmail());
            redisCacheUtils.hSet(jwtCookie.getValue(), NgtsCommonConstants.AUTH_TOKEN, responseUserObj, jwtExpirationMs );
        }catch (Exception e){
            System.out.println("Error in connecting to redis " + e);
        }

        simpMessagingTemplate.convertAndSend("/users" , responseUserObj);
        System.out.println("Session Id generated for the user " + responseUserObj.getChatUserId());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(responseUserObj);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ChatRegisterationVO signUpRequest){

       if (chatUsersService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        chatUsersService.registerforChat(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @GetMapping("/fetchAllUsers")
    public List<UserResponseVO> fetchAll() {
           return chatUsersService.findConnectedUsers();
    }


}
