package com.learning.springsecurity.helloworldsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyLogincontroller {

	@GetMapping("/mylogin")
	public String returnLogin() {
		return "mylogin";
	}
}
