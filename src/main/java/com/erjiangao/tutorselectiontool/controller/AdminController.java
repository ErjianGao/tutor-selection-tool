package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.ResponseComponent;
import com.erjiangao.tutorselectiontool.entity.Admin;
import com.erjiangao.tutorselectiontool.entity.Course;
import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.service.CourseService;
import com.erjiangao.tutorselectiontool.service.TeacherService;
import com.erjiangao.tutorselectiontool.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {
    @Autowired
    private ResponseComponent responseComponent;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    /**
     * 测试用，欢迎页
     * @return 欢迎信息
     */
    @GetMapping("")
    public Map welcome() {
        log.debug("{}", responseComponent.getUid());
        log.debug("{}", responseComponent.getRole());
        return Map.of("msg", "欢迎光临，管理员");
    }

    /**
     * 添加老师
     * @param teacher
     * @return
     */
    @PostMapping("/teachers")
    public Map addTeacher(@RequestBody Teacher teacher) {
        if (teacherService.getTeacher(teacher.getId()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "该老师已经存在，不必反复添加！");
        }
        // IdentityNo为必填内容
        teacher.setPassword(encoder.encode(teacher.getIdentityNo()));
        teacher.setRole(User.Role.TEACHER);
        teacherService.addTeacher(teacher);
        return Map.of("teacher", teacher);
    }

    /**
     * 添加课程
     * @param course 课程
     * @return 课程
     */
    @PostMapping("/courses")
    public Map addCourse(@RequestBody Course course) {
        if (courseService.getCourse(course.getId()) != null) {
            throw new ResponseStatusException((HttpStatus.BAD_REQUEST), "该课程已经存在，不必反复添加！");
        }
        courseService.addCourse(course);
        return Map.of("course", course);
    }

    /**
     * 管理员更新密码
     * @param admin 管理员的旧密码，新密码，及确认密码
     * @return 管理员
     */
    @PatchMapping("/password")
    public Map updatePassword(@RequestBody Map<String, String> admin) {
        String oldPassword = admin.get("oldPassword");
        String newPassword = admin.get("newPassword");
        String confirmNewPassword = admin.get("confirmNewPassword");
        if (!encoder.matches(oldPassword,
                userService.getUser(responseComponent.getUid()).getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "您输入的旧密码错误");
        }
        if (!newPassword.equals(confirmNewPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "你前后两次输入密码不同");
        }
        Admin a = userService.updatePassword(responseComponent.getUid(), newPassword);
        return Map.of("admin", a);
    }
}
