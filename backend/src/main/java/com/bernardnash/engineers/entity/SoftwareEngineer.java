package com.bernardnash.engineers.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SoftwareEngineer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String techStack;

    protected SoftwareEngineer() {
    }

    public SoftwareEngineer(String name, String techStack) {
        this.name = name;
        this.techStack = techStack;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTechStack() {
        return techStack;
    }
}
