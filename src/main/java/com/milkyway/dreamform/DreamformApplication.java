package com.milkyway.dreamform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DreamformApplication {

	public static void main(String[] args) {
		SpringApplication.run(DreamformApplication.class, args);
	}

}
