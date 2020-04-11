package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {
    Optional<List<Student>> findStudentsByTeacher_Id(int tid);

    Optional<List<Student>> findStudentsByElectives(int tid);

    Optional<Student> findStudentByName(String sname);

    Optional<Student> findStudentByIdentityNo(String identityNo);

    Optional<List<Student>> findByOrderByWeightedGradeDesc();
}
