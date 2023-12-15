package com.example.schoolProject.controller;

import com.example.schoolProject.domain.StudentEntity;
import com.example.schoolProject.propertyFilter.DomainPropertyFilter;
import com.example.schoolProject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentRestController {
    @Autowired
    private StudentService studentService;
    private DomainPropertyFilter filter;

    public StudentRestController() {
        this.filter = new DomainPropertyFilter("fieldFilter", new String[]{"studentList"});
    }

    @PostMapping
    public ResponseEntity<Object> createStudent(@RequestBody StudentEntity student) {
        try {
            StudentEntity saved = studentService.save(student);
            return new ResponseEntity<>(filter.getFilteredObject(saved), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> getStudent(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(filter.getFilteredObject(studentService.getStudentById(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/average/{id}")
    public ResponseEntity<Object> getAverage(@PathVariable("id") Long id) {
        try {
            double average = studentService.getStudentScoreAverage(id);
            return new ResponseEntity<>(String.format("%.2f", average), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(filter.getFilteredObject(studentService.deleteStudent(id)), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> updateStudent(@PathVariable("id") Long id, @RequestBody StudentEntity student) {
        try {
            return new ResponseEntity<>(filter.getFilteredObject(studentService.updateStudent(id, student)), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
