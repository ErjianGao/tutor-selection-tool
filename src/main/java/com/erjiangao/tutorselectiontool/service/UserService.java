package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.repository.StudentRepository;
import com.erjiangao.tutorselectiontool.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    // Student CURD
    public Student addStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student getStudent(String studentId) {
        return studentRepository.findByStudentId(studentId)
                .orElse(null);
    }

    public Student getStudent(int id) {
        return studentRepository.findById(id)
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

    // Teacher CURD
    public Teacher addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return teacher;
    }

    public Teacher getTeacher(int id) {
        return teacherRepository.findById(id)
                .orElse(null);
    }

    public Teacher getTeacher(String name) {
        return teacherRepository.findTeacherByName(name)
                .orElse(null);
    }

    public List<Teacher> listTeachers() {
        return teacherRepository.list()
                .orElse(null);
    }

    public Teacher updateTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return teacher;
    }
}
