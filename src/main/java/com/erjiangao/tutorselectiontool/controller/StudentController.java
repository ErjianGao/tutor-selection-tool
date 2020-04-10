package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.ResponseComponent;
import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.service.StudentService;
import com.erjiangao.tutorselectiontool.service.TeacherService;
import com.erjiangao.tutorselectiontool.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ResponseComponent responseComponent;

    // ----------------Direction----------------

    @ApiOperation("查看个人信息")
    @GetMapping("/profile")
    public Map getStudent() {
        return Map.of("student", studentService.getStudent(responseComponent.getUid()));
    }

    @ApiOperation("修改个人资料")
    @PatchMapping("/settings")
    public Map updateStudent(@RequestBody Student student) {
        return Map.of("student", studentService.updateStudent(student));
    }

    // ----------------Teacher----------------

    @ApiOperation("选择导师")
    @PatchMapping("/teachers/{tid}")
    public Map selectTeacher(@PathVariable int tid) {
        Teacher teacher = teacherService.getTeacher(tid);
        int countStudent = studentService.countStudents(tid);
        if (countStudent >= teacher.getMaxStudentNumber()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "抱歉，该老师已经没有名额了，请立即联系其他老师");
        }
        studentService.selectTeacher(responseComponent.getUid(), tid);
        return Map.of("teacher", teacher);
    }
}
