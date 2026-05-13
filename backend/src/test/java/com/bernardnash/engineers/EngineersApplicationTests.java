package com.bernardnash.engineers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EngineersApplicationTests {

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
		repository.save(new SoftwareEngineer(null, "Alice", "Java, Spring Boot"));
		repository.save(new SoftwareEngineer(null, "Bob", "Python, Django"));

		ResponseEntity<List> response = restTemplate.getForEntity(
				"/api/v1/software-engineers", List.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotEmpty();
		assertThat(response.getBody()).hasSize(2);
	}

}
