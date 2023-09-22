package com.learning.springsecurity.controllers.acl;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acl")
public class AclValidateController {

	
	@GetMapping(value = "/basic" , produces = MediaType.TEXT_PLAIN_VALUE)
	public String basicAcl() {
		return "jai shree sita rama lakshman hanuman";
	}
}
