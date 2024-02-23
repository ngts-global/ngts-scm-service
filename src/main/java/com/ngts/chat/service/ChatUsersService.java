package com.ngts.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngts.chat.entity.Messages;
import com.ngts.chat.entity.enum1.EChatRegistrationStatus;
import com.ngts.chat.repository.MessagesRepository;
import com.ngts.chat.vo.req.MessageVO;
import com.ngts.chat.vo.res.ChatUsersList;
import com.ngts.chat.vo.res.UsersChannelsList;
import com.ngts.chat.vo.req.ChatRegisterationVO;
import com.ngts.chat.vo.req.ChatUserVO;
import com.ngts.chat.vo.res.UserResponseVO;
import com.ngts.scm.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ngts.chat.entity.ChatUserEntity;
import com.ngts.chat.repository.ChatUserRepository;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatUsersService {

    public static String className = ChatUsersService.class.getName();

    private final ChatUserRepository repository;

    @Autowired
    MessagesRepository messagesRepository;

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

    public MessageVO findByChatId(String chatId){
        ChatUserEntity chatUserEntity = new ChatUserEntity();
        chatUserEntity.setChatId(chatId);
        List<ChatUserEntity> chatUserEntityList = repository.findAllByChatId(chatId);
        MessageVO messageResponseVO = new MessageVO();
        if(chatUserEntityList != null && chatUserEntityList.size() > 0){
            messageResponseVO.setFromId(chatUserEntityList.get(0).getChatId());
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

    public UserResponseVO getRegisteredChannels(String chatUserId){

        List<UsersChannelsList> usersChannelsList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        UserResponseVO userResponseVO = new UserResponseVO();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStreamReader in = null;
            in = new InputStreamReader(classLoader.getResourceAsStream("channel_list.json"));
            userResponseVO =   mapper.readValue(in, UserResponseVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userResponseVO.setChatUsersLists(getTopMessagesFortheUser(chatUserId));
        //getTopMessagesFortheUser(chatUserId);
       return userResponseVO;
/*
        List<ChannelRegResponseVO> channelRegResponseVOList = new ArrayList<>();

        ChannelRegResponseVO responseVO  = new ChannelRegResponseVO();
        responseVO.setChannelId(1000);
        responseVO.setChannelName("ngts dev group");
        ChannelRegResponseVO responseVO1  = new ChannelRegResponseVO();
        responseVO1.setChannelId(2000);
        responseVO1.setChannelName("biz group");
        channelRegResponseVOList.add(responseVO);
        channelRegResponseVOList.add(responseVO1);

        UsersRegisteredChannels usersRegisteredChannels = new UsersRegisteredChannels();
        usersRegisteredChannels.setChannelRegResponseVOList(channelRegResponseVOList);
        usersRegisteredChannels.setUserResponseVOList(findConnectedUsers());

        List<UsersRegisteredChannels> registeredChannelsList = new ArrayList<>();
        registeredChannelsList.add(usersRegisteredChannels);

        usersChannelsList.put(responseVO.getChannelId(), registeredChannelsList);
        usersChannelsList.put(responseVO1.getChannelId(), registeredChannelsList);

*/
      //  return usersChannelsList;
    }

    public List<ChatUsersList>  getTopMessagesFortheUser(String fromId){

        List<Messages> topMessagesList = messagesRepository.findByTopMessages(fromId);
        log.error(className + " Top messages length >> " + topMessagesList.size());


        Map<String, List<Messages>> usersMessages = topMessagesList.stream().collect(Collectors.groupingBy(data -> data.getToId()));
        Map<String, List<Messages>> usersMessages1 =  topMessagesList.stream().collect(Collectors.groupingBy(data -> data.getFromId()));
       // Map<String, List<Messages>> usersMessages2 = new HashMap<>();
        usersMessages1.forEach((s, messages) -> {
            if( s != fromId){
                if(usersMessages.containsKey(s)){
                    usersMessages.get(s).addAll(messages);
                }else {
                    usersMessages.put(s, messages);
                }
            }
        });

        usersMessages.remove(fromId); // removing my duplicate msgs
        usersMessages.forEach((s, messages) -> {
            System.out.println("-------  " + s);
            messages.stream().forEach(messages1 -> {
                System.out.println(messages1.getMsgId());
            });
        });

        List<ChatUsersList> chatUsersLists = new ArrayList<>();

        usersMessages.forEach((s, messages) -> {
            ChatUsersList chatUsers = new ChatUsersList();
            ArrayList<MessageVO> msgVoList = new ArrayList<>();
            chatUsers.setChatUserId(s);

            messages.stream().forEach(x -> {
                MessageVO messageVO = new MessageVO();
                messageVO.setFromId(x.getFromId());
                messageVO.setFromName(x.getFromName());
                messageVO.setMsgId(x.getMsgId());
                messageVO.setMsgTxt(x.getMessage());
                messageVO.setToName(x.getToName());
                messageVO.setToId(x.getToId());
                messageVO.setMsgType(x.getMessageType());
                messageVO.setStatus(x.getMsgStatus());
                messageVO.setTime(String.valueOf(x.getCreatedAt().toInstant().toEpochMilli()));
                chatUsers.setUsername(x.getFromName());
                msgVoList.add(messageVO);
            });
            chatUsers.setMessages(msgVoList);
            chatUsersLists.add(chatUsers);
        });

        return chatUsersLists;
    }
}
