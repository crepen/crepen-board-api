package com.crepen.crepenboard.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrepenBoardApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrepenBoardApiApplication.class, args);
	}

}
