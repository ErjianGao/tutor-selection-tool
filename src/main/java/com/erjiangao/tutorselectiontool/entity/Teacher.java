package com.erjiangao.tutorselectiontool.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    // the maximum of students the teacher can choose
    private int maxStudentNumber;
    // the minimum ranking of student who choose the teacher
    private int minRanking;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false,
            insertable = false)
    private LocalDateTime insertTime;

    @OneToMany(mappedBy = "teacher")
    private List<Student> students;
}
