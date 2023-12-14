package com.example.schoolProject.repository;

import com.example.schoolProject.domain.GradeEntity;
import com.example.schoolProject.domain.StudentListRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
    @Query(
            """
            SELECT new StudentListRecord(s.name, s.surname)
            FROM StudentEntity s
            JOIN GradeEntity g ON s.grade.id = g.id
            WHERE g.symbol = :symbol
            """
    )
    List<StudentListRecord> findStudentListByGradeSymbol(@Param("symbol") String gradeSymbol);

    GradeEntity findBySymbol(String symbol);
}
