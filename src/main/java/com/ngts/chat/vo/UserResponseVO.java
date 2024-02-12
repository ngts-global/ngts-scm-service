package com.ngts.chat.vo;

import java.util.HashMap;
import java.util.List;

public class UserResponseVO {

    private String chatUserId;
    private String username;
    private String email;
    private HashMap<ChannelRegResponseVO, List<UserResponseVO>> channels;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public HashMap<ChannelRegResponseVO, List<UserResponseVO>> getChannels() {
        return channels;
    }

    public void setChannels(HashMap<ChannelRegResponseVO, List<UserResponseVO>> channels) {
        this.channels = channels;
    }
}
