package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends BaseRepository<Teacher, Integer> {
    Optional<Teacher> findTeacherByName(String name);

    @Query("SELECT t FROM Teacher t")
    Optional<List<Teacher>> list();

    @Query("SELECT t FROM Teacher t WHERE t.staffIdNo=:staffIdNo")
    Optional<Teacher> findTeacherByStaffIdNo(@Param("staffIdNo") String staffIdNo);
}
