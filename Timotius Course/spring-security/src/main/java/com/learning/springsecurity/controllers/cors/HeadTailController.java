package com.learning.springsecurity.controllers.cors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cors")
//allow localhost, and local private ip: 192.168.29.116
//@CrossOrigin(origins = {"http://localhost:7001","http://192.168.29.116:7001"}, methods = {RequestMethod.GET})
public class HeadTailController {

	@GetMapping(value = "/head" , produces = MediaType.TEXT_PLAIN_VALUE)
	public String head() {
		return "HEAD";
	}
	
	@PostMapping(value = "/tail" , produces = MediaType.TEXT_PLAIN_VALUE)
	public String tail() {
		return "TAIL";
	}
}
