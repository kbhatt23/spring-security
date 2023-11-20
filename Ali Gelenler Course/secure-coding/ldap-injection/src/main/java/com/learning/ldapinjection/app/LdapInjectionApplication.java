package com.learning.ldapinjection.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.learning")
@EntityScan(basePackages = "com.learning")
public class LdapInjectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(LdapInjectionApplication.class, args);
	}
}
