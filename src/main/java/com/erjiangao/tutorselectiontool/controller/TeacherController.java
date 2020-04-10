package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.ResponseComponent;
import com.erjiangao.tutorselectiontool.entity.Course;
import com.erjiangao.tutorselectiontool.entity.Elective;
import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.service.CourseService;
import com.erjiangao.tutorselectiontool.service.StudentService;
import com.erjiangao.tutorselectiontool.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ResponseComponent responseComponent;

    // ----------------Course----------------

    @ApiOperation("查看所有选中的课程信息")
    @GetMapping("/courses")
    public Map listSelectedCourses() {
        List<Course> courses = courseService.listCourses(responseComponent.getUid());
        return Map.of("courses", courses);
    }

    @ApiOperation("选择为某课程设置要求")
    @PostMapping("/courses")
    public Map selectCourse(@RequestBody Course course) {
        if (courseService.getCourse(course.getId()) == null) {
            throw new ResponseStatusException((HttpStatus.BAD_REQUEST), "抱歉，您添加的课程不存在");
        }
        courseService.listCourses(responseComponent.getUid()).forEach(c -> {
            if (c.getId() == course.getId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您已选择过该课程，可以在“我的要求-课程要求”中查看");
            }
        });
        course.setTeacher(teacherService.getTeacher(responseComponent.getUid()));
        courseService.addCourse(course);
        return Map.of("course", course);
    }

    @ApiOperation("更新课程要求")
    @PatchMapping("/courses")
    public Map updateCourse(@RequestBody Course course) {
        if (courseService.getCourse(course.getId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，您想要修改的课程不存在");
        }

        courseService.updateCourse(course);
        return Map.of("course", course);
    }

    // ----------------Student----------------

    @ApiOperation("提前选择学生")
    @PutMapping("/students/{sid}")
    public Map selectStudent(@PathVariable int sid) {
        if (studentService.getStudent(responseComponent.getUid()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，您添加的学生不存在");
        }
        studentService.setTeacher(sid, responseComponent.getUid());
        Student student = studentService.getStudent(sid);
        return Map.of("student", student);
    }

    @ApiOperation("为课程添加学生")
    @PostMapping("/courses/{cid}/students")
    public Map addStudent(@PathVariable int cid, @RequestBody Student student) {
        Course course = courseService.getCourse(cid);
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，课程不存在");
        }
        courseService.addElective(cid, student.getId());
        return Map.of("student", student);
    }

    @ApiOperation("查询某课程学生")
    @GetMapping("/courses/{cid}/students")
    public Map listStudents(@PathVariable int cid) {
        return Map.of("students", studentService.listStudentsByCourse(cid));
    }

    @ApiOperation("互选成功学生列表")
    @GetMapping("/students")
    public Map listSelectedStudent() {
        List<Student> students = studentService.listStudents(responseComponent.getUid());
        return Map.of("students", students);
    }

    // ----------------Teacher----------------

    @ApiOperation("修改个人要求，如最低成绩，设置人数等")
    @PatchMapping("/requirements")
    public Teacher updateRequirements(@RequestBody Teacher teacher) {
        return teacherService.updateTeacher(teacher);
    }
}
