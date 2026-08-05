package com.bernardnash.engineers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SoftwareEngineerIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            // load the schema defined in data/schema.sql
            .withCopyFileToContainer(
                    MountableFile.forHostPath("data/schema.sql"),
                    "/docker-entrypoint-initdb.d/01-schema.sql");

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    SoftwareEngineerRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void addEngineerThenRetrieveIt() {
        // Given: an engineer with two tech stacks
        SoftwareEngineer alice = new SoftwareEngineer(null, "Alice", "alice@example.com");
        alice.addTechStack(new TechStack(TechStackType.JAVA));
        alice.addTechStack(new TechStack(TechStackType.SPRING_BOOT));

        // When: added via the API
        ResponseEntity<SoftwareEngineer> postResponse = restTemplate.postForEntity(
                "/api/v1/software-engineers", alice, SoftwareEngineer.class);

        // Then: it is persisted with an id
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponse.getBody()).isNotNull();
        Long id = postResponse.getBody().getId();
        assertThat(id).isNotNull();

        // And: it can be retrieved by id
        ResponseEntity<SoftwareEngineer> getResponse = restTemplate.getForEntity(
                "/api/v1/software-engineers/" + id, SoftwareEngineer.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        SoftwareEngineer retrieved = getResponse.getBody();
        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getName()).isEqualTo("Alice");
        assertThat(retrieved.getEmail()).isEqualTo("alice@example.com");
        assertThat(retrieved.getTechStacks())
                .extracting(TechStack::getTechStack)
                .containsExactlyInAnyOrder(TechStackType.JAVA, TechStackType.SPRING_BOOT);
    }
}

