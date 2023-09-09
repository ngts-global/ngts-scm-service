package com.ngts.scm.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel("")
public class StudentDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer studentId;

    private Integer transportId;

    private Integer dormitoryId;

    private String name;

    private String birthday;

    private String sex;

    private String religion;

    private String bloodGroup;

    private String address;

    private String phone;

    private String email;

    private String password;

    private String fatherName;

    private String motherName;

    private String classId;

    private String roll;

    private String dormitoryRoomNumber;

}
