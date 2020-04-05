package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    // ----------------Teacher CURD----------------
    public Teacher addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return teacher;
    }

    public Teacher getTeacher(int id) {
        return teacherRepository.findById(id)
                .orElse(null);
    }

    public Teacher getTeacher(String name) {
        return teacherRepository.findTeacherByName(name)
                .orElse(null);
    }

    public List<Teacher> listTeachers() {
        return teacherRepository.list()
                .orElse(null);
    }

    public Teacher updateTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return teacher;
    }
}
