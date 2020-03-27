package com.erjiangao.tutorselectiontool.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class DirectionSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false,
            insertable = false)
    private LocalDateTime insertTime;

    @ManyToOne
    private Student student;
    @ManyToOne
    private Direction direction;
}
