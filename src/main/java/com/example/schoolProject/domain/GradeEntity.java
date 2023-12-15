package com.example.schoolProject.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grade")
@Data
@JsonFilter("fieldFilter")
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String symbol;
    @OneToMany(mappedBy = "grade", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StudentEntity> studentList;
    @OneToOne(mappedBy = "grade")
    private TeacherEntity tutor;

    GradeEntity(){
        studentList = new ArrayList<>();
    }

    public void addStudent(StudentEntity student){
        if(studentList.contains(student))
            return;
        studentList.add(student);
    }
}
