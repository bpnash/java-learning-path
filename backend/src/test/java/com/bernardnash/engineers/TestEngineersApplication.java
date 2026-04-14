package com.bernardnash.engineers;

import org.springframework.boot.SpringApplication;

public class TestEngineersApplication {

	public static void main(String[] args) {
		SpringApplication.from(EngineersApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
