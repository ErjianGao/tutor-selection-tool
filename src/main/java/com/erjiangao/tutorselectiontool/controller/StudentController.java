package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.ResponseComponent;
import com.erjiangao.tutorselectiontool.entity.Direction;
import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.repository.DirectionRepository;
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
@RequestMapping("/api/student/")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ResponseComponent responseComponent;

    // ----------------Direction----------------

    @ApiOperation("查看个人信息")
    @GetMapping("profile")
    public Student getStudent() {
        return studentService.getStudent(responseComponent.getUid());
    }

    /**
     * 因为我一开始没考虑到位，就导致我一开始写的全是错的。还花了挺长时间调试，
     * 然后我意识到我的前端是一次性全部提交，所以应该先将原来建立的关联全部删除，然后重新建立新的关联，但是还有个问题，由于我前端传过来的只有方向的name，并且传到后端之后为了保证不重复创建相同name的对象还要加判断（这里一开始我的想法是先遍历一遍newDirections然后再遍历一遍oldDirections然后两者取交集，然后再删掉oldDirections中已经没有的部分，这个方法是在是太复杂了，最后都没实现出来，后来我一想，我如果全部提交的话，那不就相当于一个刷新操作码，所以我就把之前学生选过的方向先全部删掉，然后遍历一遍newDirections）
     * 
     * @param newDirections
     * @return
     */
    @ApiOperation("修改个人方向")
    @PatchMapping("directions")
    public List<Direction> updateStudentDirections(@RequestBody List<Direction> newDirections) {
        Student s = studentService.getStudent(responseComponent.getUid());
        List<Direction> oldDirections = s.getDirections();

        // 先将旧的方向删除
        oldDirections.forEach(direction -> {
            studentService.deleteDirection(direction.getId());
        });

        // 设置新的方向
        newDirections.forEach(direction -> {
            Direction d = studentService.getDirection(direction.getName());
            // 如果方向不存在，或者已经有其他人选过，就新建一个对象
            if (d == null || d.getStudent().getId() != s.getId()) {
                Direction newDirection = new Direction();
                newDirection.setName(direction.getName());
                newDirection.setStudent(studentService.getStudent(s.getId()));
                studentService.updateDirection(newDirection);
            }
        });
        return studentService.getStudent(s.getId()).getDirections();
    }

    @ApiOperation("查看个人方向")
    @GetMapping("directions")
    public List<Direction> getDirections() {
        return studentService.listDirections(responseComponent.getUid());
    }

    // ----------------Teacher----------------

    @ApiOperation("查看所有老师")
    @GetMapping("teachers")
    public List<Teacher> getTeacher() {
        return teacherService.listTeachers();
    }

    @ApiOperation("选择导师")
    @PatchMapping("teachers/{tid}")
    public Teacher selectTeacher(@PathVariable int tid) {
        Teacher teacher = teacherService.getTeacher(tid);
        int countStudent = studentService.countStudents(tid);
        if (countStudent >= teacher.getMaxStudentNumber()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "抱歉，该老师已经没有名额了，请立即联系其他老师");
        }
        if (studentService.checkQualification(responseComponent.getUid(), tid)) {
            Student student = studentService.getStudent(responseComponent.getUid());
            student.setTeacher(teacher);
            studentService.updateStudent(student);
        }
        return teacher;
    }
}
