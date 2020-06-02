package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.ResponseComponent;
import com.erjiangao.tutorselectiontool.entity.*;
import com.erjiangao.tutorselectiontool.service.CourseService;
import com.erjiangao.tutorselectiontool.service.StudentService;
import com.erjiangao.tutorselectiontool.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @GetMapping("{tid}/courses")
    public List<Course> listSelectedCourses(@PathVariable int tid) {
        List<Course> courses = courseService.listCourses(tid);
        return courses;
    }

    @ApiOperation("添加课程")
    @PostMapping("courses")
    public Course selectCourse(@Valid @RequestBody Course course) {
        courseService.listCourses(responseComponent.getUid()).forEach(c -> {
            if (c.getName().equals(course.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您已添加过该课程，可以在“我的要求-课程要求”中查看");
            }
        });
        course.setTeacher(teacherService.getTeacher(responseComponent.getUid()));
        courseService.addCourse(course);
        return course;
    }

    @ApiOperation("更新课程要求")
    @PatchMapping("courses")
    public Course updateCourse(@Valid @RequestBody Course course) {
        Course c = courseService.getCourse(course.getId());
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，您想要修改的课程不存在");
        }
        c.setCutOffMark(course.getCutOffMark());
        c.setWeight(course.getWeight());
        courseService.updateCourse(c);
        return c;
    }

    @ApiOperation("删除课程")
    @DeleteMapping("courses/{cid}")
    public Integer deleteCourses(@PathVariable int cid) {
        courseService.deleteCourse(cid);
        return 0;
    }

    // ----------------Student----------------

    @ApiOperation("查看所有学生")
    @GetMapping("students")
    public List<Student> listStudents() {
        return studentService.listStudents();
    }

    @ApiOperation("查看老师导入的学生")
    @GetMapping("{tid}/students")
    public List<Student> listImportedStudents(@PathVariable int tid) {
        Teacher teacher = teacherService.getTeacher(tid);
        // 先拿到老师选择的课程
        List<Course> courses = teacher.getCourses();
        List<Student> students = new ArrayList<Student>();
        courses.forEach(c -> {
            // 遍历每一门课程的选课记录
            c.getElectives().forEach(elective -> {
                students.add(elective.getStudent());
            });
        });
        return students;
    }

    @ApiOperation("提前选择学生")
    @PutMapping("students/{sid}")
    public Student selectStudent(@PathVariable int sid) {
        Teacher teacher = teacherService.getTeacher(responseComponent.getUid());
        if (studentService.getStudent(sid) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，您添加的学生不存在");
        }
        if (studentService.getStudent(sid).getTeacher() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，该学生已完成互选");
        }

        int countStudents = studentService.countStudents(responseComponent.getUid());
        if (countStudents >= teacher.getMaxStudentNumber()) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "您的学生选择数量已达上限");
        }
        Student student = studentService.getStudent(sid);
        student.setTeacher(teacher);
        studentService.updateStudent(student);
        return student;
    }

    @ApiOperation("取消互选学生")
    @DeleteMapping("selectedstudents/{sid}")
    public void cancelSelectedStudent(@PathVariable int sid) {
        Student student = studentService.getStudent(sid);
        student.setTeacher(null);
        studentService.updateStudent(student);
    }

    @ApiOperation("删除导入的学生")
    @DeleteMapping("{tid}/students/{sid}")
    public Integer deleteImportedStudents(@PathVariable int tid, @PathVariable int sid) {
        List<Course> courses = courseService.listCourses(tid);
        courses.forEach(course -> {
            course.getElectives().forEach(e -> {
                if (e.getStudent().getId() == sid) {
                    studentService.deleteElective(e.getId());
                }
            });
        });
        // 从选中的学生中同样删除
        List<Student> selectedStudents = listSelectedStudent();
        selectedStudents.forEach(selectedStudent -> {
            if (selectedStudent.getId() == sid) {
                cancelSelectedStudent(sid);
            }
        });
        return 0;
    }

    @ApiOperation("删除学生")
    @DeleteMapping("students/{sid}")
    public Integer deleteStudent(@PathVariable int sid) {
        studentService.deleteStudent(sid);
        return 0;
    }

    @ApiOperation("查询某课程学生")
    @GetMapping("courses/{cid}/students")
    public List<Student> listStudents(@PathVariable int cid) {
        return studentService.listStudentsByCourse(cid);
    }

    @ApiOperation("根据姓名查询学生")
    @GetMapping("students/{sname}")
    public Student getStudent(@PathVariable String sname) {
        return studentService.getStudent(sname);
    }

    @ApiOperation("互选成功学生列表")
    @GetMapping("selectedstudents")
    public List<Student> listSelectedStudent() {
        List<Student> students = studentService.listStudents(responseComponent.getUid());
        return students;
    }

    // ----------------Elective----------------

    @ApiOperation("批量添加选课记录")
    @PostMapping("students")
    public void addStudents(@RequestBody List<Elective> electives) {
        electives.forEach(elective -> {
            Student student = elective.getStudent();
            Student s = studentService.getStudentByIdentityNo(student.getIdentityNo());
            Course c = courseService.getCourse(elective.getCourse().getId());
            if (c == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "课程不能为空");
            }
            // 如果学生为空
            if (s == null) {
                student.setRole(User.Role.STUDENT);
                student.setPassword(encoder.encode(student.getIdentityNo()));
                studentService.addStudent(student);
                Elective e = new Elective();
                e.setStudent(student);
                e.setCourse(c);
                e.setGrade(elective.getGrade());
                courseService.addElective(e);
            } else { // 如果学生不为空
                // 找到选课记录中当前课程的记录
                Elective e = s.getElectives()
                        .stream()
                        .filter(elective1 -> elective1.getCourse().equals(c))
                        .findFirst()
                        .orElse(null);
                if (e == null) {
                    Elective newElective = new Elective();
                    newElective.setStudent(s);
                    newElective.setGrade(elective.getGrade());
                    newElective.setCourse(c);
                    courseService.addElective(newElective);
                } else {
                    // 更新选课记录
                    e.setGrade(elective.getGrade());
                    courseService.updateElective(e);
                }
            }
        });
    }

    // ----------------Direction----------------

    @ApiOperation("增加方向")
    @PostMapping("directions")
    public Direction addDirection(@Valid @RequestBody Direction direction) {
        studentService.listDirections().forEach(d -> {
            if (d.getName().equals(direction.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "此方向已经存在了，再换个其他方向吧");
            }
        });
        return studentService.addDirection(direction);
    }

    @ApiOperation("修改方向")
    @PatchMapping("directions")
    public Direction updateDirection(@Valid @RequestBody Direction direction) {
        Direction d = studentService.getDirection(direction.getId());
        if (d == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "该方向不存在");
        }
        d.setName(direction.getName());
        return updateDirection(d);
    }

    // ----------------Teacher----------------

    @ApiOperation("修改个人要求，如最低成绩，设置人数等")
    @PatchMapping("requirements/minRanking/{minRanking}/maxStudentNumber/{maxStudentNumber}")
    public Teacher updateRequirements(@PathVariable int minRanking, @PathVariable int maxStudentNumber) {
        Teacher t = teacherService.getTeacher(responseComponent.getUid());
        t.setMinRanking(minRanking);
        t.setMaxStudentNumber(maxStudentNumber);
        return teacherService.updateTeacher(t);
    }
}
