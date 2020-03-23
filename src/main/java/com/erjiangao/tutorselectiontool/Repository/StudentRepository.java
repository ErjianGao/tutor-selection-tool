package com.erjiangao.tutorselectiontool.Repository;

import com.erjiangao.tutorselectiontool.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {
}
