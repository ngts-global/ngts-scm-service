package com.ngts.scm.service;

import com.ngts.scm.dto.StudentDTO;
import com.ngts.scm.entity.Student;
import com.ngts.scm.repository.StudentRepository;
import com.ngts.scm.vo.StudentQueryVO;
import com.ngts.scm.vo.StudentUpdateVO;
import com.ngts.scm.vo.StudentVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private ModelMapper mapper;


    @Autowired
    private StudentRepository studentRepository;

    public Integer save(StudentVO vO) {
        Student bean = new Student();
        BeanUtils.copyProperties(vO, bean);
        bean = studentRepository.save(bean);
        return bean.getStudentId();
    }

    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }

    public void update(Integer id, StudentUpdateVO vO) {
        Student bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        studentRepository.save(bean);
    }

    public StudentDTO getById(Integer id) {
        Student original = requireOne(id);
        return toDTO(original);
    }

    public Page<StudentDTO> query(StudentQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private StudentDTO toDTO(Student original) {
        StudentDTO bean = new StudentDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Student requireOne(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }


    public List<StudentDTO> getAllStudents() {
        return studentRepository
                .findAll()
                .stream()
                .map(this::mapToStudentDTO)
                .collect(Collectors.toList());
    }

    private StudentDTO mapToStudentDTO(Student student) {
        return mapper.map(student, StudentDTO.class);
    }


}
