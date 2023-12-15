package com.example.schoolProject.service;

import com.example.schoolProject.domain.GradeEntity;
import com.example.schoolProject.domain.StudentEntity;
import com.example.schoolProject.repository.GradeRepository;
import com.example.schoolProject.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public StudentEntity getStudentById(Long id) throws IllegalArgumentException {
        Optional<StudentEntity> student = studentRepository.findById(id);
        if (student.isEmpty())
            throw new IllegalArgumentException("Student with given id not found!");
        return student.get();
    }

    @Override
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public StudentEntity save(StudentEntity student){
        if (student.getPesel() == null)
            throw new DataIntegrityViolationException("Pesel is required!");
        if (student.getName() == null)
            throw new DataIntegrityViolationException("Name is required!");
        if (student.getSurname() == null)
            throw new DataIntegrityViolationException("Surname is required!");

        return studentRepository.save(student);
    }

    @Override
    public double getStudentScoreAverage(Long id) throws IllegalArgumentException, IllegalStateException {
        StudentEntity student = getStudentById(id);
        if (student.getScores().isEmpty())
            throw new IllegalStateException("Student grade list is empty!");
        return student.getScores().stream().mapToDouble(x -> x).average().orElse(0);
    }

    @Override
    public StudentEntity deleteStudent(Long id) throws IllegalArgumentException {
        StudentEntity student = getStudentById(id);
        studentRepository.delete(student);
        return student;
    }

    @Override
    public StudentEntity updateStudent(Long id, StudentEntity student) throws IllegalArgumentException {
        StudentEntity record = getStudentById(id);
        record.setSurname(student.getSurname());
        record.setName(student.getName());
        record.setPesel(student.getPesel());
        record.setScores(student.getScores());
        GradeEntity grade = null;
        if(student.getGrade() != null)
            grade = gradeRepository.findById(student.getGrade().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Grade with given id not found"));
        record.setGrade(grade);
        return save(record);
    }
}
