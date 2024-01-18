package com.ngts.chat.service;

import com.ngts.auth.entity.Roles;
import com.ngts.auth.entity.UserRoles;
import com.ngts.auth.entity.Users;
import com.ngts.auth.entity.dto.UsersDTO;
import com.ngts.auth.payload.response.UserInfoResponse;
import com.ngts.chat.entity.MessageModelEntity;
import com.ngts.chat.entity.enum1.EChatRegistrationStatus;
import com.ngts.chat.vo.ChatRegisterationVO;
import com.ngts.chat.vo.ChatUserVO;
import com.ngts.chat.vo.MessageResponseVO;
import com.ngts.chat.vo.UserResponseVO;
import com.ngts.scm.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.ngts.chat.entity.ChatUserEntity;
import com.ngts.chat.repository.ChatUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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
}
