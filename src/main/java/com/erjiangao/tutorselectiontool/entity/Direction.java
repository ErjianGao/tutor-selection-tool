package com.erjiangao.tutorselectiontool.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Direction {
    // 这里方向直接使用单向一对多即可，因为不会有查询一个方向有多少个学生这个操作
    // the value of direction will be stored into database when initiate system
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min = 0, max = 200, message = "毕业设计的方向不能超过200字")
    private String name;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false,
            insertable = false)
    private LocalDateTime insertTime;
    @ManyToOne
    private Student student;
}
