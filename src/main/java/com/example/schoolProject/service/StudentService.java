package com.example.schoolProject.service;

import com.example.schoolProject.domain.StudentEntity;

import java.util.List;

public interface StudentService {
    StudentEntity getStudentById(Long id);
    StudentEntity getStudentByPesel(String pesel);
    List<StudentEntity> getAllStudents();
    StudentEntity save(StudentEntity student);
    double getStudentScoreAverage(Long id);

    void deleteStudent(Long id);
}