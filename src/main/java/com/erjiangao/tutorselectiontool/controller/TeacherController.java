package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.ResponseComponent;
import com.erjiangao.tutorselectiontool.entity.*;
import com.erjiangao.tutorselectiontool.service.CourseService;
import com.erjiangao.tutorselectiontool.service.StudentService;
import com.erjiangao.tutorselectiontool.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher/")
public class TeacherController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ResponseComponent responseComponent;

    // ----------------Course----------------

    @ApiOperation("查看所有添加的课程信息")
    @GetMapping("courses")
    public Map listSelectedCourses() {
        List<Course> courses = courseService.listCourses(responseComponent.getUid());
        return Map.of("courses", courses);
    }

    @ApiOperation("添加课程")
    @PostMapping("courses")
    public Map selectCourse(@Valid @RequestBody Course course) {
        courseService.listCourses(responseComponent.getUid()).forEach(c -> {
            if (c.getName().equals(course.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您已添加过该课程，可以在“我的要求-课程要求”中查看");
            }
        });
        course.setTeacher(teacherService.getTeacher(responseComponent.getUid()));
        courseService.addCourse(course);
        course.setTeacher(teacherService.getTeacher(responseComponent.getUid()));
        return Map.of("course", course);
    }

    @ApiOperation("更新课程要求")
    @PatchMapping("courses")
    public Map updateCourse(@Valid @RequestBody Course course) {
        Course c = courseService.getCourse(course.getId());
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，您想要修改的课程不存在");
        }
        c.setCutOffMark(course.getCutOffMark());
        c.setWeight(course.getWeight());
        courseService.updateCourse(c);
        return Map.of("course", c);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("courses/{cid}")
    public Map deleteCourses(@PathVariable int cid) {
        courseService.deleteCourse(cid);
        return Map.of("msg", "0");
    }

    // ----------------Student----------------

    @ApiOperation("查看所有学生")
    @GetMapping("students")
    public Map listStudents() {
        return Map.of("students", studentService.listStudents());
    }

    @ApiOperation("提前选择学生")
    @PutMapping("students/{sid}")
    public Map selectStudent(@PathVariable int sid) {
        Teacher teacher = teacherService.getTeacher(responseComponent.getUid());
        if (studentService.getStudent(sid) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，您添加的学生不存在");
        }
        if (studentService.getStudent(sid).getTeacher() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，该学生已完成互选");
        }

        int countStudents = studentService.countStudents(responseComponent.getUid());
        if (countStudents >= teacher.getMaxStudentNumber()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "您的学生选择数量已达上限");
        }
        Student student = studentService.getStudent(sid);
        student.setTeacher(teacher);
        studentService.updateStudent(student);
        return Map.of("student", student);
    }

    @ApiOperation("为课程添加学生")
    @PostMapping("courses/{cid}/student/{grade}")
    public Map addStudent(@PathVariable int cid, @RequestBody Student student,
                          @PathVariable double grade) {
        Course course = courseService.getCourse(cid);
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，课程不存在");
        }
        Student s = studentService.getStudentByIdentityNo(student.getIdentityNo());
        // 如果该学生不存在
        if (s == null) {
            student.setRole(User.Role.STUDENT);
            student.setPassword(encoder.encode(student.getIdentityNo()));
            studentService.addStudent(student);

            Elective elective = new Elective();
            elective.setStudent(student);
            elective.setCourse(courseService.getCourse(cid));
            elective.setGrade(grade);
            courseService.addElective(elective);
            return Map.of("student", student);
        } else {
            Elective elective = new Elective();
            elective.setStudent(s);
            elective.setCourse(courseService.getCourse(cid));
            elective.setGrade(grade);
            courseService.addElective(elective);
            return Map.of("student", s);
        }
    }

    @ApiOperation("删除学生")
    @DeleteMapping("students/{sid}")
    public Map deleteStudent(@PathVariable int sid) {
        studentService.deleteStudent(sid);
        return Map.of("msg", "0");
    }

    @ApiOperation("查询某课程学生")
    @GetMapping("courses/{cid}/students")
    public Map listStudents(@PathVariable int cid) {
        return Map.of("students", studentService.listStudentsByCourse(cid));
    }

    @ApiOperation("根据姓名查询学生")
    @GetMapping("students/{sname}")
    public Map getStudent(@PathVariable String sname) {
        return Map.of("student", studentService.getStudent(sname));
    }

    @ApiOperation("互选成功学生列表")
    @GetMapping("selectedstudents")
    public Map listSelectedStudent() {
        List<Student> students = studentService.listStudents(responseComponent.getUid());
        return Map.of("students", students);
    }

    // ----------------Direction----------------

    @ApiOperation("查看所有学生可选方向")
    @GetMapping("directions")
    public Map listDirections() {
        return Map.of("directions", studentService.listDirections());
    }

    @ApiOperation("增加方向")
    @PostMapping("directions")
    public Map addDirection(@Valid @RequestBody Direction direction) {
        studentService.listDirections().forEach(d -> {
            if (d.getName().equals(direction.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "此方向已经存在了，再换个其他方向吧");
            }
        });
        return Map.of("direction", studentService.addDirection(direction));
    }

    @ApiOperation("修改方向")
    @PatchMapping("directions")
    public Map updateDirection(@Valid @RequestBody Direction direction) {
        Direction d = studentService.getDirection(direction.getId());
        if (d == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "该方向不存在");
        }
        d.setName(direction.getName());
        return Map.of("direction", studentService.updateDirection(d));
    }

    // ----------------Teacher----------------

    @ApiOperation("修改个人要求，如最低成绩，设置人数等")
    @PatchMapping("requirements/")
    public Teacher updateRequirements(@Valid @RequestBody Teacher teacher) {
        Teacher t = teacherService.getTeacher(responseComponent.getUid());
        t.setMinRanking(teacher.getMinRanking());
        t.setMaxStudentNumber(teacher.getMaxStudentNumber());
        return teacherService.updateTeacher(t);
    }
}
