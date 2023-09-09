package com.ngts.scm.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class StudentUpdateVO extends StudentVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
