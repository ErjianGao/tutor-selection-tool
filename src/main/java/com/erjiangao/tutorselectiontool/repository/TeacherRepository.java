package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends BaseRepository<Teacher, Integer> {
    Optional<Teacher> findTeacherByName(String name);
}
