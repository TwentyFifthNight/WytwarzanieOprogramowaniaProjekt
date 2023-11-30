package com.example.schoolProject.service;

import com.example.schoolProject.domain.TeacherEntity;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface TeacherService {
    TeacherEntity getTeacherById(Long id);
    TeacherEntity getTeacherByName(String name);
    List<TeacherEntity> getAllTeachers();
    boolean exists(String name);
    TeacherEntity save(TeacherEntity teacher);

}