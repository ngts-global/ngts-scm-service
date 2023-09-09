package com.ngts.scm.controller;

import com.ngts.scm.response.MessageResponse;
import com.ngts.scm.service.StudentService;
import com.ngts.scm.vo.StudentUpdateVO;
import com.ngts.scm.vo.StudentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.NoSuchElementException;

@Api(tags = "Students API")
@Validated
@RestController
@RequestMapping("/scm/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody StudentVO vO) {
        return studentService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        studentService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody StudentUpdateVO vO) {
        studentService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public ResponseEntity<?>  getById(@Valid @NotNull @PathVariable("id") Integer id) {
        try {
             return ResponseEntity.badRequest().body(studentService.getById(id));
        }catch(NoSuchElementException exception) {
            return ResponseEntity.badRequest().body(new MessageResponse("Student Id not Found !"));
        }
    }

    /*
        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        @ApiOperation("Retrieve by query ")
        public Page<StudentDTO> query(@Valid StudentQueryVO vO) {
            return studentService.query(vO);
        }
    */
    @GetMapping("/all")
    @ApiOperation("Retrieve all students ")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok().body(studentService.getAllStudents());
    }


}
