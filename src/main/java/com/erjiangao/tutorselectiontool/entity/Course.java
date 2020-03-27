package com.erjiangao.tutorselectiontool.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private float weight;
    private float grade;
    private float cutOffMark;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false,
            insertable = false)
    private LocalDateTime insertTime;

    @OneToMany(mappedBy = "course")
    private List<Elective> electives;
}
