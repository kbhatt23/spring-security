package com.learning.resourceserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.resourceserver.dtos.UserDTO;
import com.learning.resourceserver.services.UserService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/users")
@Log4j2
public class UsersController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/sample")
	public UserDTO getSampleUser() {
		return UserDTO.builder()
					.id("108l")
					.age(108)
					.name("sample user").build();
	}
	//admin role + can delete itself only
	@PreAuthorize("hasRole('developer') and #id == #jwt.subject")
	//@Secured("ROLE_developer")
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable String id , @AuthenticationPrincipal Jwt jwt) {
		//mimicking the delete flow
		return "Deleted user: "+id;
	}
	
	//demo for post authorize
	
	@GetMapping("/{id}")
	@PostAuthorize("hasRole('developer') and #jwt.subject == returnObject.id")
	public UserDTO getUser(@PathVariable String id , @AuthenticationPrincipal Jwt jwt) {
		return userService.findUser(id);
	}
}
