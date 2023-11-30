package com.example.schoolProject.service;

import com.example.schoolProject.domain.TeacherEntity;
import com.example.schoolProject.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public TeacherEntity getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public TeacherEntity getTeacherByName(String name) {
        return teacherRepository.findByName(name);
    }

    @Override
    public List<TeacherEntity> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public boolean exists(String name) {
        return teacherRepository.findByName(name) != null;
    }

    @Override
    public TeacherEntity save(TeacherEntity teacher) {
        return teacherRepository.save(teacher);
    }
}