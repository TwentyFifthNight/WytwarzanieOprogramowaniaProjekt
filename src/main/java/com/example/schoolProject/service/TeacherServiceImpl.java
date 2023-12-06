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
    public TeacherEntity save(TeacherEntity teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        return teacherRepository.existsById(id);
    }

    @Override
    public TeacherEntity updateTeacher(Long id, TeacherEntity updatedTeacher) {
        TeacherEntity existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + id));
        return teacherRepository.save(existingTeacher);
    }
}