package com.ngts.scm.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "student_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @Column(name = "transport_id", nullable = false)
    private Integer transportId;

    @Column(name = "dormitory_id", nullable = false)
    private Integer dormitoryId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private String birthday;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "religion", nullable = false)
    private String religion;

    @Column(name = "blood_group", nullable = false)
    private String bloodGroup;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(name = "mother_name", nullable = false)
    private String motherName;

    @Column(name = "class_id", nullable = false)
    private String classId;

    @Column(name = "roll", nullable = false)
    private String roll;

    @Column(name = "dormitory_room_number", nullable = false)
    private String dormitoryRoomNumber;

}
