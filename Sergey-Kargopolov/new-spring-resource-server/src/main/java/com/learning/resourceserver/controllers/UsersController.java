package com.learning.resourceserver.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.resourceserver.dtos.UserDTO;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@GetMapping("/sample")
	public UserDTO getSampleUser() {
		return UserDTO.builder()
					.id("108l")
					.age(108)
					.name("sample user").build();
	}
}
