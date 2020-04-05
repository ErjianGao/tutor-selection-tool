package com.erjiangao.tutorselectiontool.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Teacher extends User {
    // the maximum of students the teacher can choose
    private int maxStudentNumber;
    // the minimum ranking of student who choose the teacher
    private int minRanking;

    @OneToMany(mappedBy = "teacher")
    private List<Student> students;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;
}
