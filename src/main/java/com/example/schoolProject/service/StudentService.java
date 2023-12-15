package com.example.schoolProject.service;

import com.example.schoolProject.domain.StudentEntity;

public interface StudentService {
    StudentEntity getStudentById(Long id);

    StudentEntity save(StudentEntity student);

    double getStudentScoreAverage(Long id);

    StudentEntity deleteStudent(Long id);

    StudentEntity updateStudent(Long id, StudentEntity student);
}