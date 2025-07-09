package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ForumApplication.class);
        app.run(args);
	}

}
