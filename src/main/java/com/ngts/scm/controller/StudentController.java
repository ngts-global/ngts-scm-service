package com.ngts.scm.controller;

import com.ngts.common.utils.EmailValidator;
import com.ngts.scm.dto.StudentDTO;
import com.ngts.scm.response.MessageResponse;
import com.ngts.scm.service.StudentService;
import com.ngts.scm.vo.StudentUpdateVO;
import com.ngts.scm.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

//@CrossOrigin(origins = "https://ngts-scm.web.app/*")
@Validated
@RestController
@RequestMapping("/scm/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<?>  save(@Valid @RequestBody StudentVO vO) {
        //return ResponseEntity.ok(new MessageResponse(studentService.save(vO).toString()));
        if(!EmailValidator.validate(vO.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Not a valid email address !"));
        }
        try {
            List<StudentDTO> studentDTOList = studentService.getStudentsByEmail(vO);
           if(studentDTOList.isEmpty()) {
               studentService.save(vO).toString();
               return ResponseEntity.ok().body(new MessageResponse("Student Id created in the System !"));
           }
        }catch (NoSuchElementException e){
            studentService.save(vO).toString();
            return ResponseEntity.ok().body(new MessageResponse("Student Id created in the System !"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Student record already  Found !"));
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        studentService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody StudentUpdateVO vO) {
        studentService.update(id, vO);
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok().body(studentService.getAllStudents());
    }


}
