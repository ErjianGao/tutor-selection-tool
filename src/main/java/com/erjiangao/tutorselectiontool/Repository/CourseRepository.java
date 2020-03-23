package com.erjiangao.tutorselectiontool.Repository;

import com.erjiangao.tutorselectiontool.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
