package com.example.schoolProject.domain;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected String surname;
    @Column(nullable = false, unique = true)
    protected String pesel;
}
