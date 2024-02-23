package com.ngts.chat.vo.res;

import java.util.ArrayList;

public class RegisteredChannelsResponseVO{

    public String chatUserId;
    public String username;
    public String lastLoginTime;
    public Object email;
    public ArrayList<UsersChannelsList> usersChannelsList;

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

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public ArrayList<UsersChannelsList> getUsersChannelsList() {
        return usersChannelsList;
    }

    public void setUsersChannelsList(ArrayList<UsersChannelsList> usersChannelsList) {
        this.usersChannelsList = usersChannelsList;
    }
}


