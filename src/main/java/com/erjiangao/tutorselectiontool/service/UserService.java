package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Admin;
import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.repository.AdminRepository;
import com.erjiangao.tutorselectiontool.repository.StudentRepository;
import com.erjiangao.tutorselectiontool.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private AdminRepository adminRepository;

    public User getUser(String idNo) {
        // get from students
        Student student = studentRepository.findByStudentIdNo(idNo).orElse(null);
        if (student != null) {
            return student;
        }
        Teacher teacher = teacherRepository.findTeacherByStaffIdNo(idNo).orElse(null);
        if (teacher != null) {
            return teacher;
        }
        Admin admin = adminRepository.findAdminByUsername(idNo).orElse(null);
        if (admin != null) {
            return admin;
        }
        return null;
    }
}
