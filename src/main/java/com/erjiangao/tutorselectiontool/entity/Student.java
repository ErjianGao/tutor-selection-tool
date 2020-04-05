package com.erjiangao.tutorselectiontool.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Student extends User {
    @ManyToOne
    private Teacher teacher;
    @OneToMany(mappedBy = "student")
    private List<Elective> electives;
    @OneToMany
    private List<Direction> directions;
}
