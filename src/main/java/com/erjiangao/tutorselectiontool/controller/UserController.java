package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.ResponseComponent;
import com.erjiangao.tutorselectiontool.entity.Course;
import com.erjiangao.tutorselectiontool.entity.Direction;
import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.service.CourseService;
import com.erjiangao.tutorselectiontool.service.StudentService;
import com.erjiangao.tutorselectiontool.service.TeacherService;
import com.erjiangao.tutorselectiontool.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@Validated
public class UserController {
    @Autowired
    private TeacherService teacherService;
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

    @ApiOperation("查看个人信息")
    @GetMapping("profile")
    public User getUser() {
        return userService.getUser(responseComponent.getUid());
    }

    @ApiOperation("查看所有开设课程信息")
    @GetMapping("courses")
    public List<Course> listCourses() {
        return courseService.listCourses();
    }

    @ApiOperation("查看所有学生可选方向")
    @GetMapping("directions")
    public List<Direction> listDirections() {
        return studentService.listDirections();
    }

    @ApiOperation("修改密码")
    @PatchMapping("settings/password")
    public User updatePassword(@RequestBody Map<String, String> user) {
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
        return u;
    }

    @ApiOperation("查看老师信息")
    @GetMapping("/profile/teachers/{tid}")
    public Teacher getTeacherInfo(@PathVariable int tid) {
        return teacherService.getTeacher(tid);
    }
}
