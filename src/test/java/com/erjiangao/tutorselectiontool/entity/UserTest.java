package com.erjiangao.tutorselectiontool.entity;

import com.erjiangao.tutorselectiontool.Repository.StudentRepository;
import com.erjiangao.tutorselectiontool.Repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
public class UserTest {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void test_init() {
        // create a teacher
        Teacher teacher = new Teacher();
        teacher.setName("WANG");
        teacherRepository.save(teacher);

        // create a student
        Student student = new Student();
        student.setName("GAO");
        // establish the relationship between the student and the teacher
        student.setTeacher(teacher);
        studentRepository.save(student);
    }
}
