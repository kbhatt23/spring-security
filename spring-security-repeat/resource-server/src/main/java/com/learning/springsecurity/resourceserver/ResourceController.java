package com.learning.springsecurity.resourceserver;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {
	@GetMapping
	//@PreAuthorize("#oauth2.hasScope('user_info')")
	@PreAuthorize("#oauth2.hasScope('read')")
	public String populateResource() {
		return "jai shree ram";
	}
}
