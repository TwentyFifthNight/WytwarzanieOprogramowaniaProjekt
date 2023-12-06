package com.example.schoolProject.controller;

import com.example.schoolProject.SchoolProjectApplication;
import com.example.schoolProject.domain.StudentEntity;
import com.example.schoolProject.repository.StudentRepository;
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

import java.util.ArrayList;
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
class StudentRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private StudentRepository repository;

    @Test
    public void whenValidInput_thenCreateStudent() throws Exception {
        String name = "Jan";
        String surname = "Kowalski";
        String pesel = "02222384437";
        List<Double> scores = new ArrayList<>() {
            {
                add(2.0);
                add(4.5);
            }
        };
        ObjectMapper objectMapper = new ObjectMapper();
        StudentEntity jan = new StudentEntity();
        jan.setId(null);
        jan.setName(name);
        jan.setSurname(surname);
        jan.setScores(scores);
        jan.setPesel(pesel);

        mvc.perform(post("/api/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jan)));

        List<StudentEntity> found = repository.findAll();

        assertThat(found).extracting(StudentEntity::getId)
                .isNotNull();
        assertThat(found).extracting(StudentEntity::getName)
                .contains(name);
        assertThat(found).extracting(StudentEntity::getSurname)
                .contains(surname);
        assertThat(found).extracting(StudentEntity::getScores)
                .contains(scores);
        assertThat(found).extracting(StudentEntity::getPesel)
                .contains(pesel);
    }

    @Test
    public void givenStudents_whenGetStudents_thenStatus200() throws Exception {
        String name1 = "Adam";
        String surname1 = "Kowalski";
        String pesel1 = "02222384439";
        String name2 = "Krzysztof";
        String surname2 = "Nowak";
        String pesel2 = "02222384430";

        StudentEntity student1 = new StudentEntity();
        student1.setName(name1);
        student1.setSurname(surname1);
        student1.setPesel(pesel1);
        StudentEntity student2 = new StudentEntity();
        student2.setName(name2);
        student2.setSurname(surname2);
        student2.setPesel(pesel2);

        repository.saveAndFlush(student1);
        repository.saveAndFlush(student2);

        mvc.perform(get("/api/student")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[-2].name", is(name1)))
                .andExpect(jsonPath("$[-2].surname", is(surname1)))
                .andExpect(jsonPath("$[-2].pesel", is(pesel1)))
                .andExpect(jsonPath("$[-1].name", is(name2)))
                .andExpect(jsonPath("$[-1].surname", is(surname2)))
                .andExpect(jsonPath("$[-1].pesel", is(pesel2)));
    }

    @Test
    public void givenStudents_whenStudentWithoutPesel_thenStatus400() throws Exception {
        StudentEntity student1 = new StudentEntity();
        student1.setName("Adam");
        student1.setSurname("Kowalski");


        mvc.perform(post("/api/student")
                        .content(new ObjectMapper().writeValueAsString(student1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenStudents_whenStudentWithoutNameAndSurname_thenStatus400() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        StudentEntity student1 = new StudentEntity();
        student1.setPesel("02221565579");
        student1.setSurname("Kowal");
        StudentEntity student2 = new StudentEntity();
        student2.setPesel("02221565540");
        student2.setName("Anna");

        mvc.perform(post("/api/student")
                        .content(mapper.writeValueAsString(student1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mvc.perform(post("/api/student")
                        .content(mapper.writeValueAsString(student2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenStudents_whenStudentsWithSamePesel_thenStatus400() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String pesel = "02221565579";
        StudentEntity student1 = new StudentEntity();
        student1.setPesel(pesel);
        student1.setName("Diana");
        student1.setSurname("Kowal");
        StudentEntity student2 = new StudentEntity();
        student2.setPesel(pesel);
        student2.setName("Anna");
        student2.setSurname("Jabłońska");

        mvc.perform(post("/api/student")
                        .content(mapper.writeValueAsString(student1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mvc.perform(post("/api/student")
                        .content(mapper.writeValueAsString(student2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}