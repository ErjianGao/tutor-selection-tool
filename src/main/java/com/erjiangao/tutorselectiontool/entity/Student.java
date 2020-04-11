package com.erjiangao.tutorselectiontool.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"electives", "directions"})
public class Student extends User {
    // 此属性为学生按照选择导师设置的计算规则计算出的加权成绩
    private double weightedGrade;
    private int weightedRank;

    @ManyToOne
    private Teacher teacher;
    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Elective> electives;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Direction> directions;
}
