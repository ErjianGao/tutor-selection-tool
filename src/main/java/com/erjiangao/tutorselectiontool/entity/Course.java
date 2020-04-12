package com.erjiangao.tutorselectiontool.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"electives"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;
    @Max(value = 1, message = "权重不能大于1")
    @Min(value = 0, message = "权重不能小于0")
    private float weight;
    @Max(value = 100, message = "最低分数不能超过100")
    @Min(value = 0, message = "最低分数不能低于0")
    private float cutOffMark;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false,
            insertable = false)
    private LocalDateTime insertTime;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<Elective> electives;

    // the relationship between teacher and course is oen to many
    @ManyToOne
    private Teacher teacher;
}
