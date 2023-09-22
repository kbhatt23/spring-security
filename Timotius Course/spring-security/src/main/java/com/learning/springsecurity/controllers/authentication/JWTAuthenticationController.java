package com.learning.springsecurity.controllers.authentication;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.constants.JWTAuthConstants;
import com.learning.springsecurity.dtos.JWTDataToken;
import com.learning.springsecurity.services.JWTAuthService;

@RestController
@RequestMapping("/auth/jwt")

public class JWTAuthenticationController {

	@Autowired
	private JWTAuthService service;

	// we do badic auth only for login to save cpu time in hash validation
	@PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
	public String login(HttpServletRequest request) {
		// login actual implementation
		// set the session cookie only if login was successful

		String encryptedUserName = (String) request.getAttribute(JWTAuthConstants.REQUEST_ATTRIBUTE_USERNAME);
		JWTDataToken token = new JWTDataToken();
		token.setUserName(encryptedUserName);
		return service.store(token);

		// return "login success";
	}

	// this api need to be token validated
	@GetMapping(value = "/info", produces = MediaType.TEXT_PLAIN_VALUE)
	public String info() {
		return LocalDateTime.now().toString();
	}

	//since we can not store the token in token store
	//no way to logout , expiry is only after timeout
	//for logout use hybrid jwt present in JWTRedisAuthService
	
}
