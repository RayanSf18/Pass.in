package com.rayan.passin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

@SpringBootApplication
public class PassinApplication {
	public static void main(String[] args) {
		SpringApplication.run(PassinApplication.class, args);
	}
}
