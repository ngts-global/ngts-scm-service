package com.ngts.auth.entity.dto;
import lombok.Data;
@Data
public class AuthorizationDTO {
    private static final long serialVersionUID = 1L;

    private Integer authId;
    private Integer roleId;
    private String functionUrl;
    private String funcCode;
    private String funcKey;

}
