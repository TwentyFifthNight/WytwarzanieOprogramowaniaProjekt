package com.example.schoolProject.controller;

import com.example.schoolProject.domain.StudentEntity;
import com.example.schoolProject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {
    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Object> createStudent(@RequestBody StudentEntity student) {
        HttpStatus status = HttpStatus.CREATED;
        StudentEntity saved = studentService.save(student);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping
    public ResponseEntity<List<StudentEntity>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> getStudent(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.FOUND);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/average/{id}")
    public ResponseEntity<Object> getAverage(@PathVariable("id") Long id){
        try{
            double average = studentService.getStudentScoreAverage(id);
            return new ResponseEntity<>(String.format("%.2f", average), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(studentService.deleteStudent(id), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable("id") Long id, @RequestBody StudentEntity student){
        try{
            return new ResponseEntity<>(studentService.updateStudent(id, student), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
