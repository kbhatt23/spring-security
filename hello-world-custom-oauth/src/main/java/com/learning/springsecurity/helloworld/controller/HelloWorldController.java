package com.learning.springsecurity.helloworld.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	@GetMapping("/hello-world")
	public String helloWorld(@RequestParam("name") String name ) {
		return "Hello "+ name;
	}
	
	@GetMapping("/admin/hello-world")
	public String helloWorldAdmin(@RequestParam("name") String name ) {
		return "Hello Admin"+ name;
	}
	
	
}
