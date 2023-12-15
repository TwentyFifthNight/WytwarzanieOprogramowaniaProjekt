package com.example.schoolProject.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teacher")
@Data
public class TeacherEntity extends PersonEntity{
    private String subject;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    private GradeEntity grade;
}
