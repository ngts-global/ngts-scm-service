package com.ngts.chat.service;

import com.ngts.chat.vo.ChatUserVO;
import com.ngts.scm.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ngts.chat.entity.EOnlineStatus;
import com.ngts.chat.entity.ChatUserEntity;
import com.ngts.chat.repository.ChatUserRepository;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatUsersService {

    private final ChatUserRepository repository;

    public ChatUserEntity saveUser(ChatUserVO user) {
        ChatUserEntity responseUser;
        ChatUserEntity chatUser = new ChatUserEntity();
        List<ChatUserEntity> chatUserEntityList = repository.findAllByEmail(user.getEmail());
        if(chatUserEntityList.isEmpty()){
            UUID uuid = UUID.randomUUID();
            String uniqueUserId = uuid.toString();
            chatUser.setChatId(uniqueUserId);
            chatUser.setUsername(user.getUsername());
            chatUser.setEmail(user.getEmail());
            chatUser.setStatus(EOnlineStatus.ONLINE);
            responseUser = repository.save(chatUser);
        }else{
            responseUser = chatUserEntityList.get(0);
        }

        return responseUser;
    }

    public void disconnect(ChatUserEntity user) {
        var storedUser = repository.findById(user.getEmail()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(EOnlineStatus.OFFLINE);
            repository.save(storedUser);
        }
    }

    public List<ChatUserEntity> findConnectedUsers() {
        return repository.findAllByStatus(EOnlineStatus.ONLINE);
    }

    public List<StudentDTO> getStudentsByEmail(ChatUserVO chatUserVO){
        ChatUserEntity chatUser = new ChatUserEntity();
        chatUser.setEmail(chatUserVO.getEmail());
        return null;
    }
}
