package com.example.schoolProject.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
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
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private List<StudentEntity> studentList;
    @OneToOne(mappedBy = "grade")
    private TeacherEntity tutor;

    GradeEntity(){
        studentList = new ArrayList<>();
    }
}
