package com.erjiangao.tutorselectiontool.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Slf4j
@Rollback(value = false)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void test_getStudentById() {
        int id = 2;
        log.debug(userService.getStudent(id).getName());
    }
}
