package com.example.schoolProject.controller;

import com.example.schoolProject.domain.StudentEntity;
import com.example.schoolProject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/students")
    public ResponseEntity<StudentEntity> createStudent(@RequestBody StudentEntity student) {
        HttpStatus status = HttpStatus.CREATED;
        StudentEntity saved = studentService.save(student);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/students")
    public List<StudentEntity> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{name}")
    @ResponseBody
    public StudentEntity getStudent(@PathVariable("name") String name) {
        try{
            Long id = Long.valueOf(name);
            return studentService.getStudentById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return studentService.getStudentByName(name);
        }
    }

    @GetMapping("/students/average/{id}")
    public String getAverage(@PathVariable("id") Long id){
        try{
            double average = studentService.getStudentScoreAverage(id);
            return String.format("%.2f", average);
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }
}
