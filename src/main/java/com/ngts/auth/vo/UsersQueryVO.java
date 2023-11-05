package com.ngts.auth.vo;


import lombok.Data;
import java.io.Serializable;

@Data
public class UsersQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String email;

    private String password;

    private String username;

}
