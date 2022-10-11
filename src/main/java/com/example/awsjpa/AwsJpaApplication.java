package com.example.awsjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AwsJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsJpaApplication.class, args);
	}

}
