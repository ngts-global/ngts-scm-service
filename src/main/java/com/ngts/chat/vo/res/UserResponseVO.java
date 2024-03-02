package com.ngts.chat.vo.res;

import java.util.List;

public class  UserResponseVO {

    private String chatUserId;
    private String username;
    private String email;
    private List<UsersChannelsList> usersChannelsList;
    private List<ChatUsersList> chatUsersLists;

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

    public List<UsersChannelsList> getUsersChannelsList() {
        return usersChannelsList;
    }

    public void setUsersChannelsList(List<UsersChannelsList> usersChannelsList) {
        this.usersChannelsList = usersChannelsList;
    }

    public List<ChatUsersList> getChatUsersLists() {
        return chatUsersLists;
    }

    public void setChatUsersLists(List<ChatUsersList> chatUsersLists) {
        this.chatUsersLists = chatUsersLists;
    }
}
