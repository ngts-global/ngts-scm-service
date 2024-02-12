package com.ngts.chat.service;

import com.ngts.chat.entity.enum1.EChatRegistrationStatus;
import com.ngts.chat.vo.*;
import com.ngts.scm.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ngts.chat.entity.ChatUserEntity;
import com.ngts.chat.repository.ChatUserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatUsersService {

    private final ChatUserRepository repository;

    public UserResponseVO findAllUsersByEmailAndPassword(String email, String password){
        List<ChatUserEntity> usersList =  repository.findAllUsersByEmailAndPassword(email, password)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + email));
        if(usersList != null & usersList.size() == 1){
           UserResponseVO userResponseVO = new UserResponseVO();
           ChatUserEntity recordFromUdb = usersList.get(0);
           userResponseVO.setUsername(recordFromUdb.getUsername());
           userResponseVO.setChatUserId(recordFromUdb.getChatId());
           return  userResponseVO;
        }else {
            return null;
        }
    }

    public void disconnect(ChatUserEntity user) {
        var storedUser = repository.findById(user.getEmail()).orElse(null);
        if (storedUser != null) {
            //storedUser.setStatus(EOnlineStatus.OFFLINE);
            repository.save(storedUser);
        }
    }

    public MessageResponseVO findByChatId(String chatId){
        ChatUserEntity chatUserEntity = new ChatUserEntity();
        chatUserEntity.setChatId(chatId);
        List<ChatUserEntity> chatUserEntityList = repository.findAllByChatId(chatId);
        MessageResponseVO messageResponseVO = new MessageResponseVO();
        if(chatUserEntityList != null && chatUserEntityList.size() > 0){
            messageResponseVO.setFromLogin(chatUserEntityList.get(0).getChatId());
            messageResponseVO.setFromName(chatUserEntityList.get(0).getUsername());
        }
        return messageResponseVO;
    }

    public boolean existsByEmail( String email) {
       return repository.existsByEmail(email);
    }

    public void registerforChat(ChatRegisterationVO chatRegisterationVO){
        ChatUserEntity chatUserEntity = new ChatUserEntity();
        chatUserEntity.setEmail(chatRegisterationVO.getEmail());
        chatUserEntity.setUsername(chatRegisterationVO.getUsername());
        UUID uuid = UUID.randomUUID();
        String uniqueUserId = uuid.toString();
        chatUserEntity.setChatId(uniqueUserId);
        chatUserEntity.setRegistrationStatus(EChatRegistrationStatus.PENDING.name());
        chatUserEntity.setPassword(chatRegisterationVO.getPassword());
        repository.save(chatUserEntity);
    }

    public List<UserResponseVO> findConnectedUsers() {
        List<ChatUserEntity> chatUserEntityList = repository.findAll();
        List<UserResponseVO> userResponseVOList = new ArrayList<>();
        chatUserEntityList.forEach(chatUserEntity -> {
            UserResponseVO userResponseVO = new UserResponseVO();
            userResponseVO.setChatUserId(chatUserEntity.getChatId());
            userResponseVO.setUsername(chatUserEntity.getUsername());

            userResponseVOList.add(userResponseVO);
        });
        return userResponseVOList;
    }

    public List<StudentDTO> getStudentsByEmail(ChatUserVO chatUserVO){
        ChatUserEntity chatUser = new ChatUserEntity();
        chatUser.setEmail(chatUserVO.getEmail());
        return null;
    }

    public HashMap<ChannelRegResponseVO, List<UserResponseVO>> getRegisteredChannels(){

        HashMap<ChannelRegResponseVO, List<UserResponseVO>> channelList = new HashMap<>();

        ChannelRegResponseVO responseVO  = new ChannelRegResponseVO();
        responseVO.setChannelId(1000);
        responseVO.setChannelName("ngts dev group");

        List<UserResponseVO> channelMembers = findConnectedUsers();
        channelList.put(responseVO, channelMembers);


        ChannelRegResponseVO responseVO1  = new ChannelRegResponseVO();
        responseVO1.setChannelId(2000);
        responseVO1.setChannelName("biz group");
        channelList.put(responseVO1, channelMembers);
        return channelList;
    }
}
