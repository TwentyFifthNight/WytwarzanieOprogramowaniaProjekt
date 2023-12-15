package com.example.schoolProject.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Data
@JsonFilter("fieldFilter")
public class StudentEntity extends PersonEntity{
    private List<Double> scores;
    @ManyToOne
    @JoinColumn(name = "grade_id")
    private GradeEntity grade;

    public StudentEntity() {
        scores = new ArrayList<>();
    }

    public void addScore(double score) {
        if (score > 5.0 || score < 2.0)
            throw new IllegalArgumentException("Invalid score value");
        scores.add(score);
    }
}
