package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.ResponseComponent;
import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.service.CourseService;
import com.erjiangao.tutorselectiontool.service.StudentService;
import com.erjiangao.tutorselectiontool.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;
    @Autowired
    private ResponseComponent responseComponent;

    @ApiOperation("查看所有开设课程信息")
    @GetMapping("/courses")
    public Map listCourses() {
        return Map.of("courses", courseService.listCourses());
    }

    @ApiOperation("修改密码")
    @PatchMapping("/settings/password")
    public Map updatePassword(@RequestBody Map<String, String> user) {
        String oldPassword = user.get("oldPassword");
        String newPassword = user.get("newPassword");
        String confirmNewPassword = user.get("confirmNewPassword");

        if (!encoder.matches(oldPassword,
                userService.getUser(responseComponent.getUid()).getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "您输入的旧密码错误");
        }
        if (!newPassword.equals(confirmNewPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "你前后两次输入密码不同");
        }
        User u = userService.updatePassword(responseComponent.getUid(), newPassword);
        return Map.of("user", u);
    }
}
