package com.example.schoolProject.service;

import com.example.schoolProject.domain.StudentEntity;
import com.example.schoolProject.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentEntity getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public StudentEntity getStudentByPesel(String pesel) {
        return studentRepository.findByPesel(pesel);
    }

    @Override
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public StudentEntity save(StudentEntity student) {
        return studentRepository.save(student);
    }

    @Override
    public double getStudentScoreAverage(Long id) {
        StudentEntity student = getStudentById(id);
        if(student == null)
            throw new IllegalArgumentException("Student with given id not found");

        return student.getScores().stream().mapToDouble(x -> x).average().orElse(0);
    }

    @Override
    public void deleteStudent(Long id) {

    }
}
