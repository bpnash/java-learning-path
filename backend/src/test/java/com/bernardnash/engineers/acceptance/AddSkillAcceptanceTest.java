package com.bernardnash.engineers.acceptance;

import com.bernardnash.engineers.EngineerSkillEntity;
import com.bernardnash.engineers.SkillsRepository;
import com.bernardnash.engineers.SoftwareEngineer;
import com.bernardnash.engineers.SoftwareEngineerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddSkillAcceptanceTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("engineers_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");

        // Explicitly define the driver to prevent auto-detection failure
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
    }

    @LocalServerPort
    private int port;

    @Autowired
    private SoftwareEngineerRepository softwareEngineerRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        // Skills first — engineers cascade-delete their skills
        skillsRepository.deleteAll();
        softwareEngineerRepository.deleteAll();
    }

    @Test
    void givenEngineerExists_whenAddSkillByEngineerId_thenSkillIsPersistedToDb() {
        // Given — an engineer exists in the DB
        SoftwareEngineer engineer = new SoftwareEngineer();
        engineer.setName("Alice");
        SoftwareEngineer saved = softwareEngineerRepository.save(engineer);

        // When — POST a new skill to the endpoint
        given()
            .contentType(ContentType.JSON)
            .body("""
                    {
                        "skillName": "Docker"
                    }
                    """)
        .when()
            .post("/api/v1/software-engineers/{id}/skills", saved.getId())
        .then()
            .statusCode(200);

        // Then — the skill is persisted in the DB linked to the engineer
        List<EngineerSkillEntity> skills = skillsRepository.findByEngineerId(saved.getId().longValue());
        assertEquals(1, skills.size());
        assertEquals("Docker", skills.get(0).getSkillName());
    }
}

