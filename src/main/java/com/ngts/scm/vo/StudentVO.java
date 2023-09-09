package com.ngts.scm.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel("Save ")
public class StudentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "studentId can not null")
    private Integer studentId;

    @NotNull(message = "transportId can not null")
    private Integer transportId;

    @NotNull(message = "dormitoryId can not null")
    private Integer dormitoryId;

    @NotNull(message = "name can not null")
    private String name;

    @NotNull(message = "birthday can not null")
    private String birthday;

    @NotNull(message = "sex can not null")
    private String sex;

    @NotNull(message = "religion can not null")
    private String religion;

    @NotNull(message = "bloodGroup can not null")
    private String bloodGroup;

    @NotNull(message = "address can not null")
    private String address;

    @NotNull(message = "phone can not null")
    private String phone;

    @NotNull(message = "email can not null")
    private String email;

    @NotNull(message = "password can not null")
    private String password;

    @NotNull(message = "fatherName can not null")
    private String fatherName;

    @NotNull(message = "motherName can not null")
    private String motherName;

    @NotNull(message = "classId can not null")
    private String classId;

    @NotNull(message = "roll can not null")
    private String roll;

    @NotNull(message = "dormitoryRoomNumber can not null")
    private String dormitoryRoomNumber;

}
