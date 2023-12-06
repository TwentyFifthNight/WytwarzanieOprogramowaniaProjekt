package com.example.schoolProject.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentEntityTest {

    private StudentEntity student;

    @BeforeEach
    void setUp() {
        student = new StudentEntity();
    }

    @Test
    void givenStudent_whenNewStudent_thenStudentNotNullAndScoresNotNull() {
        assertNotNull(student);
        assertNotNull(student.getScores());
        assertNull(student.getId());
        assertNull(student.getName());
        assertNull(student.getSurname());
        assertNull(student.getPesel());
    }

    @Test
    void givenStudent_whenSetValues_thenGetValues() {
        Long id = 1L;
        String name = "Adam";
        String surname = "Kowalski";

        student.setId(id);
        student.setName(name);
        student.setSurname(surname);

        assertEquals(id, student.getId());
        assertEquals(name, student.getName());
        assertEquals(surname, student.getSurname());
    }

    @Test
    void givenStudent_whenAddInvalidScore_thenThrowInvalidArgument() {
        List<Double> invalidScores = new ArrayList<>() {
            {
                add(5.1);
                add(1.9);
            }
        };
        invalidScores.forEach(x -> assertThrows(IllegalArgumentException.class, () -> student.addScore(x)));
    }

    @Test
    void givenStudent_whenAddValidScore_thenGetScores() {
        List<Double> scores = new ArrayList<>() {
            {
                add(5.0);
                add(2.0);
                add(2.5);
                add(4.5);
            }
        };
        scores.forEach(x -> student.addScore(x));
        scores.forEach(x -> assertTrue(student.getScores().contains(x)));
    }

    @Test
    void givenStudent_whenSetValidScores_thenGetScores() {
        List<Double> scores = new ArrayList<>() {
            {
                add(5.0);
                add(2.0);
                add(2.5);
                add(4.5);
            }
        };
        student.setScores(scores);
        scores.forEach(x -> assertTrue(student.getScores().contains(x)));
    }
}