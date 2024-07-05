package org.gobeshona.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

@SpringBootApplication(exclude = { FlywayAutoConfiguration.class })
public class GobeshonaQA {

	public static void main(String[] args) {
		SpringApplication.run(GobeshonaQA.class, args);
	}

}
