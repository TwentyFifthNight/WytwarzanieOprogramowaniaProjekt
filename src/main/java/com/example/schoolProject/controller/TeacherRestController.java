package com.example.schoolProject.controller;

import com.example.schoolProject.domain.TeacherEntity;
import com.example.schoolProject.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherRestController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public ResponseEntity<Object> createTeacher(@RequestBody TeacherEntity teacher) {
        try {
            TeacherEntity saved = teacherService.save(teacher);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<TeacherEntity>> getAllTeachers() {
        return new ResponseEntity<>(teacherService.getAllTeachers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTeacherById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(teacherService.getTeacherById(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTeacher(@PathVariable("id") Long id) {
        try {
            teacherService.deleteTeacher(id);
            return new ResponseEntity<>("Teacher deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTeacher(
            @PathVariable("id") Long id,
            @RequestBody TeacherEntity updatedTeacher) {
        try {
            TeacherEntity teacher = teacherService.updateTeacher(id, updatedTeacher);
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}