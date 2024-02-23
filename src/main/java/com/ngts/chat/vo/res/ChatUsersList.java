package com.ngts.chat.vo.res;

import com.ngts.chat.vo.req.MessageVO;

import java.util.ArrayList;

public class ChatUsersList {

    private String chatUserId;
    private String username;
    private String email;
    public ArrayList<MessageVO> messages;


    public String getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(String chatUserId) {
        this.chatUserId = chatUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<MessageVO> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageVO> messages) {
        this.messages = messages;
    }
}
