package com.learning.mongoinjection.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.learning" })
@EntityScan(basePackages = { "com.learning" })
@EnableMongoRepositories(basePackages = { "com.learning" })
public class MongoInjectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoInjectionApplication.class, args);
	}
}
