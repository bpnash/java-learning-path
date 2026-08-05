-- Schema for the engineers Postgres database

DROP TABLE IF EXISTS techstacks;
DROP TABLE IF EXISTS engineers;
DROP TYPE IF EXISTS techstack_type;

-- Enum of allowed tech stack values
CREATE TYPE techstack_type AS ENUM (
    'JAVA',
    'SPRING_BOOT',
    'PYTHON',
    'DJANGO',
    'JAVASCRIPT',
    'TYPESCRIPT',
    'REACT',
    'NODE'
);

CREATE TABLE engineers (
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- One engineer -> many techstacks
CREATE TABLE techstacks (
    id          BIGSERIAL PRIMARY KEY,
    engineer_id BIGINT NOT NULL,
    techstack   techstack_type NOT NULL,
    CONSTRAINT fk_techstacks_engineer FOREIGN KEY (engineer_id)
        REFERENCES engineers (id)
        ON DELETE CASCADE,
    CONSTRAINT uq_engineer_techstack UNIQUE (engineer_id, techstack)
);

CREATE INDEX idx_techstacks_engineer_id ON techstacks (engineer_id);

