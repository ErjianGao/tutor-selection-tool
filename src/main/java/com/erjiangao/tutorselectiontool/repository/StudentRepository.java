package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {
    @Query("SELECT s FROM Student s WHERE s.studentId=:studentId")
    Optional<Student> findByStudentId(@Param("studentId") String studentId);

    @Query("SELECT s FROM Student s")
    Optional<List<Student>> list();
}
