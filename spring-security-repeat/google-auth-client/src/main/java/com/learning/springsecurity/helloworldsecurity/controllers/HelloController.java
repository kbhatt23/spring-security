package com.learning.springsecurity.helloworldsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping("/hello")
	public String returnLogin() {
		//dispatches to hello.html view
		return "hello";
	}
}
