package com.example.schoolProject.repository;

import com.example.schoolProject.domain.TeacherEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    List<TeacherEntity> findAll();
}