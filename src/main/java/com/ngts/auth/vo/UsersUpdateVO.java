package com.ngts.auth.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsersUpdateVO extends UsersVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
