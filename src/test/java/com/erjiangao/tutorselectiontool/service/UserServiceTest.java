package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Slf4j
@Rollback(value = false)
public class UserServiceTest {
    @Autowired
    private StudentService studentService;
    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void test_addStudent() {
        Student student = new Student();
        student.setName("GAO");
        student.setPassword(encoder.encode("123456"));
        student.setRole(User.Role.STUDENT);
        student.setIdentityNo("201703");
        studentService.addStudent(student);
    }
}
