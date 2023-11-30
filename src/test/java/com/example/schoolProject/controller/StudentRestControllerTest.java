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
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SchoolProjectApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase
class StudentRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private StudentRepository repository;

    @Test
    public void whenValidInput_thenCreateStudent() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        StudentEntity john = new StudentEntity();
        john.setId(null);
        john.setName("John");
        john.setSurname("Smith");
        john.setScores(new ArrayList<>(){
            {
                add(2.0);
            }
        });

        mvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(john)));
        List<StudentEntity> found = repository.findAll();

        assertThat(found).extracting(StudentEntity::getName)
                .contains("John Smiths");
    }
}