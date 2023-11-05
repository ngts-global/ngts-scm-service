package com.ngts.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "user_roles")
public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pk_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk_id;

    @Column(name = "role_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name = "user_id")
    private Long userId;

}
