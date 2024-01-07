package com.ngts.chat.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "chat_user_reg_status")
public class ChatUsersStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pk_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk_id;

    @Column(name = "chat_reg_status_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chat_user_reg_status_Id;

    @Column(name = "chat_user_id")
    private Integer chat_user_Id;



}