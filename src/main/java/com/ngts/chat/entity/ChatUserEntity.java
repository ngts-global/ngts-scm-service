package com.ngts.chat.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "chat_users")
public class ChatUserEntity {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "chatId", nullable = false)
    private String chatId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "status", nullable = false)
    private EOnlineStatus status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
