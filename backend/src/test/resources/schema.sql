DROP TABLE IF EXISTS engineer_skills;
DROP TABLE IF EXISTS software_engineers;

CREATE TABLE software_engineers (
    id        BIGSERIAL    PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    tech_stack VARCHAR(255)
);

CREATE TABLE engineer_skills (
    id               BIGSERIAL    PRIMARY KEY,
    engineer_id      BIGINT       NOT NULL,
    skill_name       VARCHAR(255) NOT NULL,
    experience_years INTEGER,
    CONSTRAINT fk_engineer_skills_engineer
        FOREIGN KEY (engineer_id)
        REFERENCES software_engineers (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_engineer_skills_engineer_id ON engineer_skills (engineer_id);

