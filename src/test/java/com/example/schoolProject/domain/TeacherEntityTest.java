package com.example.schoolProject.domain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherEntityTest {

    private TeacherEntity teacher;

    @BeforeEach
    void setUp() {
        teacher = new TeacherEntity();
    }

    @Test
    void givenTeacher_whenNewTeacher_thenTeacherNotNull() {
        assertNotNull(teacher);
    }

    @Test
    void givenTeacher_whenSetValues_thenGetValues() {
        Long id = 1L;
        String name = "Jan";
        String surname = "Duzy";
        String subject = "Matematyka";
        String pesel = "12312312312";

        teacher.setId(id);
        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.setSubject(subject);
        teacher.setPesel(pesel);

        assertEquals(id, teacher.getId());
        assertEquals(name, teacher.getName());
        assertEquals(surname, teacher.getSurname());
        assertEquals(subject, teacher.getSubject());
        assertEquals(pesel, teacher.getPesel());
    }
}