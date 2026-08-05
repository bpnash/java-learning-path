package com.bernardnash.engineers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;

@Entity
@Table(name = "techstacks")
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "engineer_id", nullable = false)
    @JsonIgnore
    private SoftwareEngineer engineer;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM) // maps to the native Postgres enum techstack_type
    @Column(name = "techstack", nullable = false)
    private TechStackType techStack;

    public TechStack() {
    }

    public TechStack(TechStackType techStack) {
        this.techStack = techStack;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SoftwareEngineer getEngineer() {
        return engineer;
    }

    public void setEngineer(SoftwareEngineer engineer) {
        this.engineer = engineer;
    }

    public TechStackType getTechStack() {
        return techStack;
    }

    public void setTechStack(TechStackType techStack) {
        this.techStack = techStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechStack that = (TechStack) o;
        return Objects.equals(id, that.id) && techStack == that.techStack;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, techStack);
    }
}

