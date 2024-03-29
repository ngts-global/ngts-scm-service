package com.ngts.chat.controller;

import com.ngts.auth.jwt.AuthJwtUtils;
import com.ngts.auth.payload.response.MessageResponse;
import com.ngts.chat.service.ChatUsersService;
import com.ngts.chat.utils.SessionUtils;
import com.ngts.chat.vo.SessionRoles;
import com.ngts.chat.vo.req.*;
import com.ngts.chat.vo.res.ChatUsersList;
import com.ngts.chat.vo.res.AuthenticationResponse;
import com.ngts.chat.vo.res.UserResponseVO;
import com.ngts.common.constants.NgtsCommonConstants;
import com.ngts.common.redis.RedisCacheUtils;
import com.ngts.common.utils.EmailValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/comm/chat")
public class ChatUsersController {

    public static String className = ChatUsersController.class.getName();

    @Autowired
    AuthJwtUtils jwtUtils;

    @Autowired
    SessionUtils sessionUtils;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Value("${ngtsscm.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatUsersService chatUsersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ChatUserVO chatUser, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(className + " handling register user request: " + chatUser.getEmail());
        AuthenticationResponse pinValidationResponse = new AuthenticationResponse();

        UserResponseVO responseUserObj = chatUsersService.findAllUsersByEmailAndPassword(chatUser.getEmail(), chatUser.getPassword());
        if(responseUserObj == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Bad credentials !"));
        }
        ResponseCookie jwtCookie = null;

        System.out.println(className + " Login is success so creating new session. ");
        HttpSession session = request.getSession(true);
        System.out.println(className + " Session created " + session);

        if(session != null){
            System.out.println(className + " Session ID " + session.getId());
            sessionUtils.setCustomerProfile(request, responseUserObj);
            sessionUtils.setCurrentRole(request, SessionRoles.DEMOTED);
        }

        try{
            jwtCookie = jwtUtils.generateJwtCookie(chatUser.getEmail());
            redisCacheUtils.hSet(jwtCookie.getValue(), NgtsCommonConstants.AUTH_TOKEN, responseUserObj, jwtExpirationMs );
        }catch (Exception e){
            System.out.println(className + " Error in connecting to redis " + e);
        }


        /*UserResponseVO tempResponseVo = chatUsersService.getRegisteredChannels(responseUserObj.getChatUserId());
        responseUserObj.setUsersChannelsList(tempResponseVo.getUsersChannelsList());
        responseUserObj.setChatUsersLists(tempResponseVo.getChatUsersLists());*/


        pinValidationResponse.setIsValid("");
        pinValidationResponse.setActionCode("PIN");
        pinValidationResponse.setToken(jwtCookie.toString());
        pinValidationResponse.setStatusMsg("PIN_VALIDATE");
        pinValidationResponse.setUserResponseVO(responseUserObj);

        simpMessagingTemplate.convertAndSend("/users" , responseUserObj); // This is to broadcast when the user is online.

        System.out.println(className + " User unique Id generated for the user " + responseUserObj.getChatUserId());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(pinValidationResponse);
    }

    @PostMapping("/validatePin")
    public ResponseEntity<?> validatePin(@Valid @RequestBody PinRequestVO pinRequestVO, HttpServletRequest request, HttpServletResponse response) {
        AuthenticationResponse pinValidationResponse = new AuthenticationResponse();
        if(pinRequestVO.getInputPin() == null || pinRequestVO.getInputPin().length() <5){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Pin has to be corrected!"));
        }
        //SessionRoles sessionRoles = sessionUtils.getCurrentRoles(request);
        //if(sessionRoles.equals(SessionRoles.DEMOTED)){
                String pin = pinRequestVO.getInputPin();
                log.error(" User entered Pin " + pin);
                //UserResponseVO userResponseVO =  sessionUtils.getCustomerProfile(request);
                //log.error(" Email from profile object " + userResponseVO.getEmail() + " , Chat Id " + userResponseVO.getChatUserId());

                UserResponseVO responseUserObj =   chatUsersService.findByChatIdAndPin(pinRequestVO.getChatId(), Integer.parseInt(pin));
                if(responseUserObj == null){
                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Bad credentials !"));
                }

            UserResponseVO tempResponseVo = chatUsersService.getRegisteredChannels(responseUserObj.getChatUserId());
            responseUserObj.setUsersChannelsList(tempResponseVo.getUsersChannelsList());
            responseUserObj.setChatUsersLists(tempResponseVo.getChatUsersLists());

            pinValidationResponse.setIsValid("");
            pinValidationResponse.setActionCode("Dashboard");
            pinValidationResponse.setToken(""); // Once Pin validated we need to generate new token and keep it for long time.
            pinValidationResponse.setStatusMsg("DASHBOARD");
            pinValidationResponse.setUserResponseVO(responseUserObj);

       // }else {
        //    return ResponseEntity.badRequest().body(new MessageResponse("Error: Please validate login credentials first !"));
       // }

        return ResponseEntity.ok().body(pinValidationResponse);
    }

    @PostMapping("/createPin")
    public ResponseEntity<?> createPin(@Valid @RequestBody PinRequestVO pinRequestVO, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/init")
    public ResponseEntity<?> initHanshakeCall(@Valid @RequestBody PinRequestVO pinRequestVO, HttpServletRequest request, HttpServletResponse response) {


            AuthenticationResponse pinValidationResponse = new AuthenticationResponse();

            return ResponseEntity.ok().body("");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ChatRegisterationVO signUpRequest){

        if(signUpRequest.getUsername() == null || signUpRequest.getUsername().length() <5){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username has to be corrected!"));
        }
        if(signUpRequest.getEmail() == null || signUpRequest.getEmail().length() <5){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username has to be corrected!"));
        }
        if(!EmailValidator.validate(signUpRequest.getEmail())){
            return ResponseEntity.badRequest().body(new com.ngts.scm.response.MessageResponse("Error: Not a valid email address !"));
        }
        if(signUpRequest.getPassword() == null || signUpRequest.getPassword().length() <5){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Password has to be corrected!"));
        }
       if (chatUsersService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        chatUsersService.registerforChat(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/messages")
    public ResponseEntity<?> getUsersMessages(@RequestBody MessageRequestVO messageRequestVO, HttpServletRequest request, HttpServletResponse response){
        log.error(className + " Requesting all messages for " + messageRequestVO.getChatUserId());
     /*   UserResponseVO customerProfile = sessionUtils.getCustomerProfile(request);
        if(customerProfile == null){
            log.error(className + " error in reading customer profile ");
        } else {
            log.error(className + " customer profile details  " + customerProfile.getChatUserId() + " , Name " + customerProfile.getUsername());
        }*/

        List<ChatUsersList> msgList = chatUsersService.getMessagesFor2Users(messageRequestVO.getChatUserId(), messageRequestVO.getChatUserId());
        return ResponseEntity.ok().body(msgList);
    }

    @PostMapping("/createChannels")
    public ResponseEntity<?> createChannels(@Valid @RequestBody ChannelRegistrationRequestVO channelRegistrationVO, HttpServletRequest request, HttpServletResponse response){

        if(channelRegistrationVO.getChannelName() == null || channelRegistrationVO.getChannelName().length() <5){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not a valid Channel Name !"));
        }
        return ResponseEntity.ok(new MessageResponse("Channel is created successfully You can add people !"));
    }

    @PostMapping("/addToChannels")
    public ResponseEntity<?> addPeopleToChannels(@Valid @RequestBody ChatRegisterationVO signUpRequest, HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/fetchAllUsers")
    public List<UserResponseVO> fetchAll(HttpServletRequest request, HttpServletResponse response) {
           return chatUsersService.findConnectedUsers();
    }


}
