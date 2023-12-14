package com.example.schoolProject.service;

import com.example.schoolProject.domain.GradeEntity;
import com.example.schoolProject.repository.GradeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GradeServiceImpl implements GradeService{
    @Autowired
    private GradeRepository gradeRepository;
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
        GradeEntity gradeToUpdate = findById(id);
        gradeToUpdate.setSymbol(grade.getSymbol());
        return gradeRepository.save(gradeToUpdate);
    }
}
