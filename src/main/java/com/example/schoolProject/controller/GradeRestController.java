package com.example.schoolProject.controller;

import com.example.schoolProject.domain.GradeEntity;
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

    @PostMapping
    public ResponseEntity<Object> createGrade(@RequestBody GradeEntity grade) {
        try {
            GradeEntity saved = gradeService.save(grade);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> getGrade(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(gradeService.findById(id), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{symbol}")
    @ResponseBody
    public ResponseEntity<Object> getGrade(@PathVariable("symbol") String symbol) {
        try {
            return new ResponseEntity<>(gradeService.findBySymbol(symbol), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
