package com.example.schoolProject.service;

import com.example.schoolProject.domain.GradeEntity;
import com.example.schoolProject.domain.StudentEntity;
import com.example.schoolProject.repository.GradeRepository;
import com.example.schoolProject.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class GradeServiceImpl implements GradeService{
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public GradeEntity findById(Long id) throws IllegalArgumentException {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grade with given id not found!"));
    }

    @Override
    public GradeEntity findBySymbol(String symbol) throws IllegalArgumentException{
        GradeEntity grade = gradeRepository.findBySymbol(symbol);
        if(grade == null)
            throw new IllegalArgumentException("Grade with given symbol not found!");
        return grade;
    }

    @Override
    public GradeEntity save(GradeEntity grade) {
        return gradeRepository.save(grade);
    }

    @Override
    public GradeEntity delete(Long id) throws IllegalArgumentException {
        GradeEntity grade = findById(id);
        gradeRepository.delete(grade);
        return grade;
    }

    @Override
    public GradeEntity update(Long id, GradeEntity grade) throws IllegalArgumentException {
        GradeEntity record = findById(id);
        record.setSymbol(grade.getSymbol());
        return gradeRepository.save(record);
    }

    @Override
    public GradeEntity addStudent(Long id, GradeEntity grade) {
        GradeEntity record = findById(id);
        if(grade.getStudentList().isEmpty())
            throw new IllegalArgumentException("No student");
        for(StudentEntity studentIn : grade.getStudentList()){
            Optional<StudentEntity> student = studentRepository.findById(studentIn.getId());
            if(student.isEmpty())
                continue;
            StudentEntity studentEntity = student.get();
            studentEntity.setGrade(record);
            record.addStudent(studentEntity);
        }

        return gradeRepository.save(record);
    }


}
