package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Course;
import com.erjiangao.tutorselectiontool.entity.Elective;
import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.repository.CourseRepository;
import com.erjiangao.tutorselectiontool.repository.ElectiveRepository;
import org.apache.catalina.User;
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
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ElectiveRepository electiveRepository;

    // Course CURD
    public Course addCourse(Course course) {
        courseRepository.save(course);
        return course;
    }

    public void deleteCourseById(int id) {
        courseRepository.deleteById(id);
    }

    public List<Course> listCourses() {
        return courseRepository.list()
                .orElse(null);
    }

    public Course getCourse(int id) {
        return courseRepository.findById(id)
                .orElse(null);
    }

    public Course updateCourse(Course course) {
        courseRepository.save(course);
        return course;
    }

    // Elective CURD
    public Elective addElective(int cid, int sid) {
        Student student = userService.getStudent(sid);
        Course course = courseService.getCourse(cid);
        Elective elective = new Elective();
        elective.setCourse(course);
        elective.setStudent(student);
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
        return electiveRepository.list(sid)
                .orElse(null);
    }
}
