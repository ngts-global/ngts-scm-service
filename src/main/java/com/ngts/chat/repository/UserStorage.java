package com.ngts.chat.repository;

import com.ngts.chat.entity.ChatUserEntity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserStorage {

    private static UserStorage instance;
    private Set<ChatUserEntity> users;

    private UserStorage() {
        users = new HashSet<>();
    }

    public static synchronized UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    public Set<ChatUserEntity> getUsers() {
        return users;
    }

    public void setUser(ChatUserEntity userName) throws Exception {
        if(userName.getUsername().equalsIgnoreCase("clear")){
             instance = new UserStorage();
        }
        Map<String, Set<ChatUserEntity>> emailToObjMap =
                users.stream()
                        .collect(Collectors.groupingBy(ChatUserEntity::getEmail,
                                Collectors.toSet()));

        if (emailToObjMap.containsKey(userName.getEmail())) {
            throw new Exception("User aready exists with userName: " + userName);
        }
        users.add(userName);
    }
}
