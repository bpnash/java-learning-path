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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EngineersApplicationTests {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
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
	void contextLoads() {
	}

	@Test
	void getAllEngineers_returnsNonEmptyList() {
		repository.save(new SoftwareEngineer(null, "Alice", "alice@example.com"));
		repository.save(new SoftwareEngineer(null, "Bob", "bob@example.com"));

		ResponseEntity<List> response = restTemplate.getForEntity(
				"/api/v1/software-engineers", List.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotEmpty();
		assertThat(response.getBody()).hasSize(2);
	}

}
