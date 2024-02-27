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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ngts.chat.entity.ChatUserEntity;
import com.ngts.chat.repository.ChatUserRepository;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
    try{
        List<CompletableFuture<List<ChatUsersList>>> allUsersAndChatMsgList = chatUserEntityList.stream().map(userResponseEntity -> asyncChatUserList(userResponseEntity.getChatId())).collect(Collectors.toList());
        log.error(className + " Total parallel task " + allUsersAndChatMsgList.size());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(allUsersAndChatMsgList.toArray(new  CompletableFuture[allUsersAndChatMsgList.size()]));

        CompletableFuture<List<List<ChatUsersList>>> allFutureList = allFutures.thenApply(v -> {
            return allUsersAndChatMsgList.stream()
                    .map(data -> data.join())
                    .collect(Collectors.toList());
        });

        List<ChatUsersList> finalList = new ArrayList<>();
        allFutureList.get().stream().forEach(chatUsersLists -> {
            //log.error(" Second Size >>> " + chatUsersLists.size());
            chatUsersLists.stream().forEach(chatUserObj -> {
             //   log.error("instance inside list name : " + chatUserObj.getUsername()+", chat Id :  " + chatUserObj.getChatUserId() + " ,  Total " + chatUserObj.getMessages().size());
                finalList.add(chatUserObj);

            });
        });
        //log.error(">>>>>>>>> Total messages " + finalList.size());
        Map<String, List<ChatUsersList>> chatUsers =   finalList.stream().collect(Collectors.groupingBy(data -> data.getChatUserId()));
        //log.error(" >>>>>>>>>>> " + chatUsers);
        chatUserEntityList.forEach(chatUserEntity -> {
            UserResponseVO userResponseVO = new UserResponseVO();
            userResponseVO.setChatUserId(chatUserEntity.getChatId());
            userResponseVO.setUsername(chatUserEntity.getUsername());
            if(chatUsers.containsKey(chatUserEntity.getChatId())){
                userResponseVO.setChatUsersLists(chatUsers.get(chatUserEntity.getChatId()));
            }else {

            }
            userResponseVOList.add(userResponseVO);
        });
    }catch (Exception e){
       log.error(className + " Error in executing the fetch all users " + e);
    }
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
        long startTime = System.currentTimeMillis();
        userResponseVO.setChatUsersLists(getTopMessagesFortheUser(chatUserId));
        log.error(" Time taken for getTopMessagesFortheUser " + (System.currentTimeMillis() - startTime));
        return userResponseVO;
    }

    public List<ChatUsersList>  getTopMessagesFortheUser(String fromId){

        List<Messages> topMessagesList = messagesRepository.findByFromId(fromId);
        log.error(className + " Top messages length >> " + topMessagesList.size());
        topMessagesList.stream().forEach(messages -> {
            log.error(messages.getFromId() + " " + messages.getToId() + " " + messages.getFromName() + " " + messages.getToName() + " " + messages.getMessage());
        });

        Map<String, List<Messages>> usersMessages = topMessagesList.stream().collect(Collectors.groupingBy(data -> data.getToId()));
        Map<String, List<Messages>> usersMessages1 =  topMessagesList.stream().collect(Collectors.groupingBy(data -> data.getFromId()));

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
                if(messageVO.getFromId().equalsIgnoreCase(s)) {
                    chatUsers.setUsername(x.getFromName());
                }else {
                    chatUsers.setUsername(x.getToName());
                }
                msgVoList.add(messageVO);
            });
            chatUsers.setMessages(msgVoList);
            chatUsersLists.add(chatUsers);
        });

        return chatUsersLists;
    }

    public List<ChatUsersList>  getMessagesFor2Users(String fromId, String toId){
        List<Messages> topMessagesList = messagesRepository.findMessagesBetweenUsers(fromId, toId);
        log.error(className + " Top messages length >> " + topMessagesList.size());
        topMessagesList.stream().forEach(messages -> {
            log.error(messages.getFromId() + " " + messages.getToId() + " " + messages.getFromName() + " " + messages.getToName() + " " + messages.getMessage());
        });

        Map<String, List<Messages>> usersMessages = topMessagesList.stream().collect(Collectors.groupingBy(data -> data.getToId()));
        Map<String, List<Messages>> usersMessages1 =  topMessagesList.stream().collect(Collectors.groupingBy(data -> data.getFromId()));

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
                if(messageVO.getFromId().equalsIgnoreCase(s)) {
                    chatUsers.setUsername(x.getFromName());
                }else {
                    chatUsers.setUsername(x.getToName());
                }
                msgVoList.add(messageVO);
            });
           // chatUsers.setMessages(msgVoList);
            chatUsersLists.add(chatUsers);
        });

        return chatUsersLists;
    }


    public List<UsersChannelsList>  getTopChannelUsers(String fromId){

        List<UsersChannelsList> topChannelList = new ArrayList<>();
        return topChannelList;
    }


    private CompletableFuture<List<ChatUsersList>> asyncChatUserList(String chatUserId){
        CompletableFuture<List<ChatUsersList>> chatUsersFuture = CompletableFuture.supplyAsync(()->{
            return getTopMessagesFortheUser(chatUserId);
        });
        return chatUsersFuture;
    }

}
