package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findTeacherByName(String name);

    @Query("SELECT t FROM Teacher t")
    Optional<List<Teacher>> list();

    // @Query("DELETE FROM Teacher t WHERE t.name=:name")
    void deleteTeacherByName(String name);
}
