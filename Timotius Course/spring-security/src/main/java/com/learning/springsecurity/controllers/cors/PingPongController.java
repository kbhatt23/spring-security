package com.learning.springsecurity.controllers.cors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cors")
public class PingPongController {

	//@CrossOrigin
	@GetMapping(value = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
	public String ping() {
		return "PING";
	}

	@PostMapping(value = "/pong", produces = MediaType.TEXT_PLAIN_VALUE)
	public String pong() {
		return "PONG";
	}
}
