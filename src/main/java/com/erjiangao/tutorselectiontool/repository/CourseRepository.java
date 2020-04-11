package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends BaseRepository<Course, Integer> {
    Optional<List<Course>> findCoursesByTeacher_Id(int tid);
}
