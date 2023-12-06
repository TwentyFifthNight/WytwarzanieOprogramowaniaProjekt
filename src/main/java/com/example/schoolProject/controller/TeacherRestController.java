package com.example.schoolProject.controller;

import com.example.schoolProject.domain.TeacherEntity;
import com.example.schoolProject.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherRestController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherEntity> createTeacher(@RequestBody TeacherEntity teacher) {
        HttpStatus status = HttpStatus.CREATED;
        TeacherEntity saved = teacherService.save(teacher);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping
    public List<TeacherEntity> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<TeacherEntity> getTeacherById(@PathVariable("id") Long id) {
        TeacherEntity teacherById = teacherService.getTeacherById(id);
        if (teacherById != null) {
            return ResponseEntity.ok(teacherById);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("id") Long id) {
        HttpStatus status = HttpStatus.OK;
        if (teacherService.exists(id)) {
            teacherService.deleteTeacherById(id);
            return new ResponseEntity<>("Teacher deleted successfully", status);
        } else {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>("Teacher not found", status);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeacher(
            @PathVariable("id") Long id,
            @RequestBody TeacherEntity updatedTeacher) {
        HttpStatus status = HttpStatus.OK;
        if (teacherService.exists(id)) {
            TeacherEntity existingTeacher = teacherService.getTeacherById(id);
            existingTeacher.setName(updatedTeacher.getName());
            existingTeacher.setSurname(updatedTeacher.getSurname());
            existingTeacher.setSubject(updatedTeacher.getSubject());

            TeacherEntity saved = teacherService.save(existingTeacher);
            return new ResponseEntity<>("Teacher updated successfully", status);
        } else {
            status = HttpStatus.NOT_FOUND;
            return ResponseEntity.status(status).body("Teacher not found");
        }
    }
}