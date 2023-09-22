package com.learning.springsecurity.controllers.authentication;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.constants.JWTAuthConstants;
import com.learning.springsecurity.dtos.JWERedisDataToken;
import com.learning.springsecurity.services.JWERedisAuthService;

@RestController
@RequestMapping("/auth/jwe-redis")

public class JWERedisAuthenticationController {

	@Autowired
	private JWERedisAuthService service;

	// we do badic auth only for login to save cpu time in hash validation
	@PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
	public String login(HttpServletRequest request) {
		// login actual implementation
		// set the session cookie only if login was successful

		String encryptedUserName = (String) request.getAttribute(JWTAuthConstants.REQUEST_ATTRIBUTE_USERNAME);
		JWERedisDataToken token = new JWERedisDataToken();
		token.setUserName(encryptedUserName);
		return service.store(token);
		// return "login success";
	}

	// this api need to be token validated
	@GetMapping(value = "/info", produces = MediaType.TEXT_PLAIN_VALUE)
	public String info() {
		return LocalDateTime.now().toString();
	}

	//also storing jwt id in redis hence can logout
	@DeleteMapping(value="/logout", produces = MediaType.TEXT_PLAIN_VALUE)
	public String logout(HttpServletRequest request) {
		service.delete(request.getAttribute(JWTAuthConstants.JWT_ID).toString());
		return "logged out";
	}
}
