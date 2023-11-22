package com.example.schoolProject.repository;

import com.example.schoolProject.domain.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    StudentEntity findByName(String name);
    List<StudentEntity> findAll();
}
