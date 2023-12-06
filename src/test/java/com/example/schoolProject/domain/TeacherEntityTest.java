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
    void testDefaultConstructor() {
        assertNotNull(teacher);
        assertNull(teacher.getName());
        assertNull(teacher.getSurname());
        assertNull(teacher.getSubject());
    }

    @Test
    void givenTeacher_whenSetValues_thenGetValues() {
        Long id = 1L;
        String name = "Jan";
        String surname = "Duzy";
        String subject = "Matematyka";

        teacher.setId(id);
        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.setSubject(subject);

        assertEquals(id, teacher.getId());
        assertEquals(name, teacher.getName());
        assertEquals(surname, teacher.getSurname());
        assertEquals(subject, teacher.getSubject());
    }
}