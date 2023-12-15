package com.example.schoolProject.service;

import com.example.schoolProject.domain.GradeEntity;

public interface GradeService {
    GradeEntity findById(Long id);
    GradeEntity findBySymbol(String symbol);
    GradeEntity save(GradeEntity grade);
    GradeEntity delete(Long id);
    GradeEntity update(Long id, GradeEntity grade);
    GradeEntity addStudent(Long id, GradeEntity grade);
}
