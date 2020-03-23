package com.erjiangao.tutorselectiontool.Repository;

import com.erjiangao.tutorselectiontool.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
