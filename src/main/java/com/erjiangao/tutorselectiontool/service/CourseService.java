package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Course;
import com.erjiangao.tutorselectiontool.entity.Elective;
import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.repository.CourseRepository;
import com.erjiangao.tutorselectiontool.repository.ElectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ElectiveRepository electiveRepository;

    // ----------------Course CURD----------------
    // 新建课程，管理员完成
    public Course addCourse(Course course) {
        courseRepository.save(course);
        return course;
    }

    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(int id) {
        return courseRepository.findById(id)
                .orElse(null);
    }

    public Course updateCourse(Course course) {
        courseRepository.save(course);
        return course;
    }

    // ----------------Elective CURD----------------
    public Elective addElective(Elective elective) {
        electiveRepository.save(elective);
        return elective;
    }

    public void deleteElective(int id) {
        electiveRepository.deleteById(id);
    }

    public Elective getElective(int id) {
        return electiveRepository.findById(id)
                .orElse(null);
    }

    public List<Elective> listElectives(int sid) {
        return electiveRepository.findElectivesByStudent_Id(sid)
                .orElse(List.of());
    }

    // ----------------Teacher select course Service----------------
    public void addCourseByTeacherId(Course course, int tid) {
        Teacher teacher = teacherService.getTeacher(tid);
        course.setTeacher(teacher);
        courseRepository.save(course);
    }

    public List<Course> listCourses(int tid) {
        return courseRepository.findCoursesByTeacher_Id(tid)
                .orElse(List.of());
    }
}
