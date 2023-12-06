package com.example.schoolProject.service;

import com.example.schoolProject.domain.TeacherEntity;
import com.example.schoolProject.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public TeacherEntity getTeacherById(Long id) {
        Optional<TeacherEntity> teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()) {
            throw new IllegalArgumentException("Teacher with given id not found!");
        }
        return teacher.get();
    }

    @Override
    public List<TeacherEntity> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public TeacherEntity save(TeacherEntity teacher) {
        try {
            return teacherRepository.save(teacher);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Required fields are missing or duplicate entry!");
        }
    }

    @Override
    public void deleteTeacherById(Long id) {
        if (!exists(id)) {
            throw new IllegalArgumentException("Teacher with given id not found!");
        }
        teacherRepository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        return teacherRepository.existsById(id);
    }

    @Override
    public TeacherEntity updateTeacher(Long id, TeacherEntity updatedTeacher) {
        TeacherEntity existingTeacher = getTeacherById(id);
        existingTeacher.setName(updatedTeacher.getName());
        existingTeacher.setSurname(updatedTeacher.getSurname());
        existingTeacher.setSubject(updatedTeacher.getSubject());
        return teacherRepository.save(existingTeacher);
    }
}