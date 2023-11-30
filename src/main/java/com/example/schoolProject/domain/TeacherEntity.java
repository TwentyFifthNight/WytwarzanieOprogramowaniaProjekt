package com.example.schoolProject.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teacher")
@Data
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String subject;
}
