package com.example.schoolProject.service;

import com.example.schoolProject.domain.TeacherEntity;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface TeacherService {
    TeacherEntity getTeacherById(Long id);

    List<TeacherEntity> getAllTeachers();

    boolean exists(Long id);

    TeacherEntity save(TeacherEntity teacher);

    void deleteTeacher(Long id);

    TeacherEntity updateTeacher(Long id, TeacherEntity updatedTeacher);
}