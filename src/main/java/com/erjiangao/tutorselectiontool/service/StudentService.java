package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    // Student CURD
    public Student getStudent(int sid) {
        return studentRepository.findById(sid).orElse(null);
    }

    // Student CURD
    public Student addStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student getStudent(String studentIdNo) {
        return studentRepository.findByStudentIdNo(studentIdNo)
                .orElse(null);
    }

    public List<Student> listStudents() {
        return studentRepository.list()
                .orElse(null);
    }

    public Student updateStudent(Student student) {
        studentRepository.save(student);
        return student;
    }
}
