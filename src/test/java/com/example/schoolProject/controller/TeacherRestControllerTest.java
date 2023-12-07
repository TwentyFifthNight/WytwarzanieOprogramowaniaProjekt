package com.example.schoolProject.controller;

import com.example.schoolProject.SchoolProjectApplication;
import com.example.schoolProject.domain.TeacherEntity;
import com.example.schoolProject.repository.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SchoolProjectApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:application-tests.properties")
@AutoConfigureTestDatabase
class TeacherRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TeacherRepository repository;

    @Test
    public void whenValidInput_thenCreateTeacher() throws Exception {
        String name = "John";
        String surname = "Doe";
        String subject = "Math";
        String pesel = "12345678907";

        TeacherEntity teacher = new TeacherEntity();
        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.setSubject(subject);
        teacher.setPesel(pesel);

        ObjectMapper mapper = new ObjectMapper();
        mvc.perform(post("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacher)))
                .andExpect(status().isCreated());

        List<TeacherEntity> found = repository.findAll();
        assertThat(found).extracting(TeacherEntity::getId).isNotNull();
        assertThat(found).extracting(TeacherEntity::getName).contains(name);
        assertThat(found).extracting(TeacherEntity::getSurname).contains(surname);
        assertThat(found).extracting(TeacherEntity::getSubject).contains(subject);
        assertThat(found).extracting(TeacherEntity::getPesel).contains(pesel);
    }

    @Test
    public void givenTeachers_whenGetTeachers_thenStatus200() throws Exception {
        TeacherEntity teacher1 = new TeacherEntity();
        teacher1.setName("John");
        teacher1.setSurname("Doe");
        teacher1.setSubject("Math");
        teacher1.setPesel("12345678901");

        TeacherEntity teacher2 = new TeacherEntity();
        teacher2.setName("Jane");
        teacher2.setSurname("Smith");
        teacher2.setSubject("Science");
        teacher2.setPesel("10987654321");

        repository.saveAndFlush(teacher1);
        repository.saveAndFlush(teacher2);

        mvc.perform(get("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[-2].name", is("John")))
                .andExpect(jsonPath("$[-2].surname", is("Doe")))
                .andExpect(jsonPath("$[-2].subject", is("Math")))
                .andExpect(jsonPath("$[-2].pesel", is("12345678901")))
                .andExpect(jsonPath("$[-1].name", is("Jane")))
                .andExpect(jsonPath("$[-1].surname", is("Smith")))
                .andExpect(jsonPath("$[-1].subject", is("Science")))
                .andExpect(jsonPath("$[-1].pesel", is("10987654321")));
    }

    @Test
    public void givenTeachers_whenTeacherWithoutPesel_thenStatus400() throws Exception {
        TeacherEntity teacherWithoutPesel = new TeacherEntity();
        teacherWithoutPesel.setName("John");
        teacherWithoutPesel.setSurname("Doe");
        teacherWithoutPesel.setSubject("Math");

        mvc.perform(post("/api/teacher")
                        .content(new ObjectMapper().writeValueAsString(teacherWithoutPesel))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenTeachers_whenTeacherWithoutNameAndSurname_thenStatus400() throws Exception {
        TeacherEntity teacherWithoutNameAndSurname1 = new TeacherEntity();
        teacherWithoutNameAndSurname1.setSubject("Physics");
        teacherWithoutNameAndSurname1.setPesel("12345678903");

        TeacherEntity teacherWithoutNameAndSurname2 = new TeacherEntity();
        teacherWithoutNameAndSurname2.setSurname("Smith");
        teacherWithoutNameAndSurname2.setPesel("98765432105");

        ObjectMapper mapper = new ObjectMapper();
        mvc.perform(post("/api/teacher")
                        .content(mapper.writeValueAsString(teacherWithoutNameAndSurname1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mvc.perform(post("/api/teacher")
                        .content(mapper.writeValueAsString(teacherWithoutNameAndSurname2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenTeachers_whenTeachersWithSamePesel_thenStatus400() throws Exception {
        TeacherEntity teacher1 = new TeacherEntity();
        teacher1.setName("John");
        teacher1.setSurname("Doe");
        teacher1.setSubject("Math");
        teacher1.setPesel("12345676902");

        TeacherEntity teacher2 = new TeacherEntity();
        teacher2.setName("Alice");
        teacher2.setSurname("Smith");
        teacher2.setSubject("Physics");
        teacher2.setPesel("12345676902");

        ObjectMapper mapper = new ObjectMapper();
        mvc.perform(post("/api/teacher")
                        .content(mapper.writeValueAsString(teacher1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mvc.perform(post("/api/teacher")
                        .content(mapper.writeValueAsString(teacher2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}