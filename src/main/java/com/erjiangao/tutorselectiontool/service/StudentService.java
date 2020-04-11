package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.*;
import com.erjiangao.tutorselectiontool.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ElectiveRepository electiveRepository;
    @Autowired
    private CourseRepository courseRepository;

    // ----------------Student CURD----------------
    public Student getStudent(int sid) {
        return studentRepository.findById(sid).orElse(null);
    }

    public Student getStudent(String sname) {
        return studentRepository.findStudentByName(sname).orElse(null);
    }

    public Student getStudentByIdentityNo(String identityNo) {
        return studentRepository.findStudentByIdentityNo(identityNo).orElse(null);
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public List<Student> listStudents(int tid) {
        return studentRepository.findStudentsByTeacher_Id(tid)
                .orElse(List.of());
    }

    public List<Student> listStudentsByCourse(int cid) {
        List<Elective> electives = electiveRepository.findElectivesByCourse_IdOrderByGradeDesc(cid)
                .orElse(List.of());
        List<Student> students = new ArrayList<>();
        electives.forEach(e -> {
            Student student = e.getStudent();
            students.add(student);
        });
        return students;
    }

    public int countStudents(int tid) {
        return (int)studentRepository.count();
    }

    public Student updateStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public void deleteStudent(int sid) {
        studentRepository.deleteById(sid);
    }

    // 计算学生根据所选导师的综合得分
    public double computeWeightedGrade(int sid, int tid) {
        Student student = getStudent(sid);
        List<Course> courses = courseService.listCourses(tid);
        double average = 0;
        for (Course c : courses) {
            List<Elective> electives =
                    electiveRepository.findElectivesByCourse_IdOrderByGradeDesc(c.getId())
                            .orElse(List.of());
            for (Elective e : electives) {
                if (e.getStudent().getId() == sid) {
                    average += e.getGrade() * c.getWeight();
                }
            }
        }
        student.setWeightedGrade(average);
        // studentRepository.save(student);
        return student.getWeightedGrade();
    }

    // 查询排名
    public int getweightedRank(int sid, int tid) {
        Student student = studentService.getStudent(sid);
        Teacher teacher = student.getTeacher();
        // 得到所有学生的序列，按成绩排序
        List<Student> students = listRankedStudents(teacher.getId());
        int weightedRank = 0;
        for (Student s : students) {
            weightedRank++;
            if (s.getId() == student.getId()) {
                break;
            }
        }
        return weightedRank;
    }

    // 检查是否满足要求
    public boolean checkQualification(int sid, int tid) {
        Student student = getStudent(sid);
        List<Elective> electives = listElectives(sid);
        // 判断是否每门课程都达标
        for (Elective elective : electives) {
            Course course = courseService.getCourse(elective.getCourse().getId());
            if (elective.getGrade() < course.getCutOffMark()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您的" + course.getName() + "成绩不达标，该课程老师规定的成绩为"
                                + course.getCutOffMark() + "，请尽快联系其他老师");
            }
        }
        Teacher teacher = teacherService.getTeacher(tid);
        int weightedRank = getweightedRank(student.getId(), tid);
        if (weightedRank > teacher.getMinRanking()) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您的综合成绩排名不达标，该课程老师规定的最低排名为"
                    + teacher.getMinRanking() + "，请尽快联系其他老师");
        }
        return true;
    }

    // 列出所有按照老师自己标准排序的学生
    public List<Student> listRankedStudents(int tid) {
        List<Student> students = listStudents();
        Map<Student, Double> tempGradeMap = new HashMap<>();
        students.forEach(s -> {
            double tempGrade = computeWeightedGrade(s.getId(), tid);
            tempGradeMap.put(s, tempGrade);
        });
        return tempGradeMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // ----------------Direction CURD----------------
    public Direction addDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }

    public Direction getDirection(int id) {
        return directionRepository.findById(id)
                .orElse(null);
    }

    public List<Direction> listDirections() {
        return directionRepository.findAll();
    }

    public List<Direction> listDirections(int sid) {
        return directionRepository.findAll();
    }

    public Direction updateDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }

    // ----------------Electives CURD----------------
    
    public List<Elective> listElectives(int sid) {
        return electiveRepository.findElectivesByStudent_Id(sid).orElse(List.of());
    }
}
