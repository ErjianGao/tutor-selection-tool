package com.erjiangao.tutorselectiontool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String studentId;
    private String name;
    private String password;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false,
            insertable = false)
    private LocalDateTime insertTime;

    @ManyToOne
    private Teacher teacher;
    @OneToMany(mappedBy = "student")
    private List<Elective> electives;
    @OneToMany(mappedBy = "student")
    private List<DirectionSelection> directionSelections;
}
