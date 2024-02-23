package com.ngts.chat.controller;

import com.ngts.auth.payload.response.MessageResponse;
import com.ngts.chat.service.ChannelService;
import com.ngts.chat.vo.req.ChannelRegistrationRequestVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/comm/channels")
public class ChannelController {

    public static String className = ChannelController.class.getName();

    public ChannelService channelService;

    @PostMapping("/create")
    public ResponseEntity<?> createChannels(@Valid @RequestBody ChannelRegistrationRequestVO channelRegistrationRequestVO, HttpServletRequest request, HttpServletResponse response) {
        if(channelRegistrationRequestVO.getChannelName() == null || channelRegistrationRequestVO.getChannelName().length() <5){
            log.error(className + " Invalid channel name " + channelRegistrationRequestVO.getChannelName());
            return ResponseEntity.badRequest().body(new MessageResponse("Error: name has to be corrected!"));
        }

        channelService.channelCreate(channelRegistrationRequestVO);
        return ResponseEntity.ok(new MessageResponse("Channel created successfully!"));
    }



}
