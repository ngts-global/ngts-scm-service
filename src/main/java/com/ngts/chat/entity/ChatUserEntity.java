package com.ngts.chat.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import com.ngts.chat.entity.enum1.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@Data
@Entity
@Table(name = "chat_users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class ChatUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "chatId", nullable = false)
    private String chatId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "pin", nullable = false)
    private Integer pin;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "valid_upto", nullable = false)
    private OffsetDateTime validupto;


    @NotBlank
    @Size(max = 120)
    private String password;

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    @Column(name = "registration_status", nullable = false)
    private String registrationStatus = EChatRegistrationStatus.PENDING.name();

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

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OffsetDateTime getValidupto() {
        return validupto;
    }

    public void setValidupto(OffsetDateTime validupto) {
        this.validupto = validupto;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }
}
