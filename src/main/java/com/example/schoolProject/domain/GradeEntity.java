package com.example.schoolProject.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "grade")
@Data
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String symbol;
    @OneToMany(mappedBy = "grade", fetch = FetchType.LAZY)
    private List<StudentEntity> studentList;
    @OneToOne
    private TeacherEntity tutor;
}
