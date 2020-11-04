package com.learning.springsecurity.helloworldsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/default-security")
public class HelloDefaultSecurityController {
	//public - > no need of crednetials
	@GetMapping("/say-hello/{name}")
	public String sayHello(@PathVariable String name) {
		return "jai shree ram says public member "+name;
	}
	
	//private -> need crednetils with admin role
	@GetMapping("/private/say-hello/{name}")
	public String sayHelloPrivate(@PathVariable String name) {
		return "jai shree ram says private member "+name;
	}
	
	//user role based user
	@GetMapping("/user/say-hello/{name}")
	public String sayHelloUserRole(@PathVariable String name) {
		return "jai shree ram says user role member "+name;
	}
}
