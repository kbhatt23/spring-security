package com.learning.springsecurity.helloworldsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//adding below for allowing oauth2 resttemplate
public class HelloWorldSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldSecurityApplication.class, args);
	}

}
