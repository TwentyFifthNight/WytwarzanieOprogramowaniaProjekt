package com.example.schoolProject.controller;

import com.example.schoolProject.domain.TeacherEntity;
import com.example.schoolProject.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherRestController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/teachers")
    public ResponseEntity<TeacherEntity> createTeacher(@RequestBody TeacherEntity teacher) {
        HttpStatus status = HttpStatus.CREATED;
        TeacherEntity saved = teacherService.save(teacher);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/teachers")
    public List<TeacherEntity> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/teachers/{name}")
    @ResponseBody
    public TeacherEntity getTeacher(@PathVariable("name") String name) {
        try{
            Long id = Long.valueOf(name);
            return teacherService.getTeacherById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return teacherService.getTeacherByName(name);
        }
    }
}