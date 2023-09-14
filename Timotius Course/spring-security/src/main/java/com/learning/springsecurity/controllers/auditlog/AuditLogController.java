package com.learning.springsecurity.controllers.auditlog;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.dtos.OriginalStringRequest;

//generating audit logs demo code
//generate random status code
@RestController
@RequestMapping("/audit")
public class AuditLogController {

	@GetMapping(value = "/random-status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> randomStatusCode(@RequestBody(required = true) OriginalStringRequest originalStringRequest) {
		
		int nextInt = ThreadLocalRandom.current().nextInt(3);
		
		int randomStatus = switch (nextInt) {
		case 0 -> HttpStatus.OK.value();
		case 1 -> HttpStatus.CREATED.value();
		case 2 -> HttpStatus.BAD_REQUEST.value();
		default -> HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.value();
		
		};
		
		return ResponseEntity.status(randomStatus).body(LocalDateTime.now().toString());
		
	}

}
