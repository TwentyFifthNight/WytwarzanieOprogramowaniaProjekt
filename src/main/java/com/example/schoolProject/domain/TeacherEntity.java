package com.example.schoolProject.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teacher")
@Data
public class TeacherEntity extends PersonEntity{
    private String subject;
}
