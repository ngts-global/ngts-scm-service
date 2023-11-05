package com.ngts.auth.entity.dto;


import lombok.Data;
import java.io.Serializable;

@Data
public class UsersDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String email;

    private String password;

    private String username;

}
