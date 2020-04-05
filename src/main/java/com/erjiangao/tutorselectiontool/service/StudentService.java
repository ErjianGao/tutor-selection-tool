package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Direction;
import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.repository.DirectionRepository;
import com.erjiangao.tutorselectiontool.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DirectionRepository directionRepository;

    // ----------------Student CURD----------------
    public Student getStudent(int sid) {
        return studentRepository.findById(sid).orElse(null);
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public List<Student> listStudents() {
        return studentRepository.list()
                .orElse(null);
    }

    public Student updateStudent(Student student) {
        studentRepository.save(student);
        return student;
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
        return directionRepository.list()
                .orElse(null);
    }

    public List<Direction> listDirections(int sid) {
        return directionRepository.list()
                .orElse(null);
    }

    public Direction updateDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }
}
