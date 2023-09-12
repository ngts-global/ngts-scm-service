package com.ngts.common.redis.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEventObj implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer studentId;
    private String name;
    private String birthday;
    private String fatherName;
    private String address;
    private String phone;
    private String email;

}
