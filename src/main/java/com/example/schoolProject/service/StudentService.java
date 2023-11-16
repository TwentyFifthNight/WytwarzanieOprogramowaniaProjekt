package com.example.schoolProject.service;

import com.example.schoolProject.domain.StudentEntity;

import java.util.List;

public interface StudentService {
    StudentEntity getStudentById(Long id);
    StudentEntity getStudentByName(String name);
    List<StudentEntity> getAllStudents();
    boolean exists(String name);
    StudentEntity save(StudentEntity student);
    double getStudentScoreAverage(Long id);
}