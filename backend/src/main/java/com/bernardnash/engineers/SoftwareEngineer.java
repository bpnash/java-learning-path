package com.bernardnash.engineers;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public SoftwareEngineer() {
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
        return techStacks;
    }

    public void setTechStacks(List<TechStack> techStacks) {
        List<TechStack> copy = techStacks == null ? new ArrayList<>() : new ArrayList<>(techStacks);
        this.techStacks.clear();
        copy.forEach(this::addTechStack);
    }

    public void addTechStack(TechStack techStack) {
        techStack.setEngineer(this);
        this.techStacks.add(techStack);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoftwareEngineer that = (SoftwareEngineer) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
