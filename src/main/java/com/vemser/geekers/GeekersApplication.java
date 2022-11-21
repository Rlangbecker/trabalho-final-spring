package com.vemser.geekers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class GeekersApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeekersApplication.class, args);
	}

}
