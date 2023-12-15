package com.example.schoolProject.controller;

import com.example.schoolProject.domain.GradeEntity;
import com.example.schoolProject.propertyFilter.DomainPropertyFilter;
import com.example.schoolProject.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grade")
public class GradeRestController {
    @Autowired
    private GradeService gradeService;
    private DomainPropertyFilter filter;

    public GradeRestController() {
        this.filter = new DomainPropertyFilter("fieldFilter", new String[]{"grade"});
    }

    @PostMapping
    public ResponseEntity<Object> createGrade(@RequestBody GradeEntity grade) {
        try {
            return new ResponseEntity<>(filter.getFilteredObject(gradeService.save(grade)), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/studentList/{id}")
    public ResponseEntity<Object> getGradeStudentList(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(filter.getFilteredObject(gradeService.findById(id).getStudentList()), HttpStatus.FOUND);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> getGrade(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(filter.getFilteredObject(gradeService.findById(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGrade(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(filter.getFilteredObject(gradeService.delete(id)), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/addStudent/{id}")
    public ResponseEntity<Object> addStudent(@PathVariable("id") Long id, @RequestBody GradeEntity grade){
        try{
            return new ResponseEntity<>(filter.getFilteredObject(gradeService.addStudent(id, grade)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
