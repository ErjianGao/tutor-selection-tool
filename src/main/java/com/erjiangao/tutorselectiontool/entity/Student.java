package com.erjiangao.tutorselectiontool.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String studentNumber;
    private String name;

    @ManyToOne
    private Teacher teacher;
    @OneToMany(mappedBy = "student")
    private List<Elective> electives;
    @OneToMany(mappedBy = "student")
    private List<DirectionSelection> directions;
}
