package com.ngts.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "scm_authorization")
public class Authorization implements Serializable {

    @Id
    @Column(name = "auth_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authId;

    @Column(name = "role_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name="func_url")
    private String functionUrl;

    @Column(name="func_code")
    private String funcCode;

    @Column(name = "func_key")
    private String funcKey;


}
