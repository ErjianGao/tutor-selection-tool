package com.erjiangao.tutorselectiontool.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"courses", "students"})
public class Teacher extends User {
    // the maximum of students the teacher can choose
    @Max(value = 129)
    @PositiveOrZero
    private int maxStudentNumber;
    // the minimum ranking of student who choose the teacher
    @Max(value = 129)
    @PositiveOrZero
    private int minRanking;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    private List<Student> students;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    private List<Course> courses;
}
