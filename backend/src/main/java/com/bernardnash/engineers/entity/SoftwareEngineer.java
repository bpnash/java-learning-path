package com.bernardnash.engineers.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "engineers")
public class SoftwareEngineer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechStack> techStacks = new ArrayList<>();

    protected SoftwareEngineer() {
    }

    public SoftwareEngineer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public SoftwareEngineer(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TechStack> getTechStacks() {
        return List.copyOf(techStacks);
    }

    public void addTechStack(TechStack techStack) {
        techStack.setEngineer(this);
        techStacks.add(techStack);
    }
}
