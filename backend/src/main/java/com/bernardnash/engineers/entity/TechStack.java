package com.bernardnash.engineers.entity;

import com.bernardnash.engineers.model.TechStackType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "techstacks")
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "engineer_id", nullable = false)
    private SoftwareEngineer engineer;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "techstack", nullable = false)
    private TechStackType techStack;

    protected TechStack() {
    }

    public TechStack(TechStackType techStack) {
        this.techStack = techStack;
    }

    public TechStackType getTechStack() {
        return techStack;
    }

    public void setEngineer(SoftwareEngineer engineer) {
        this.engineer = engineer;
    }
}
