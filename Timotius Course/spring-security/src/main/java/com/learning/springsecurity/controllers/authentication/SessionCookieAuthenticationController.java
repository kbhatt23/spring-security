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

import com.learning.springsecurity.constants.SessionCookieConstants;
import com.learning.springsecurity.dtos.SessionCookieToken;
import com.learning.springsecurity.services.SessionCookieService;

@RestController
@RequestMapping("/auth/session-cookie")

public class SessionCookieAuthenticationController {
	
	@Autowired
	private SessionCookieService service;

	//we do badic auth only for login to save cpu time in hash validation
	@PostMapping(value="/login", produces = MediaType.TEXT_PLAIN_VALUE)
	public String login(HttpServletRequest request){
		//login actual implementation
		//set the session cookie only if login was successful
		
		String encryptedUserName = (String)request.getAttribute(SessionCookieConstants.REQUEST_ATTRIBUTE_USERNAME);
		SessionCookieToken sessionCookieToken = new SessionCookieToken();
		sessionCookieToken.setUserName(encryptedUserName);
		return service.store(request, sessionCookieToken);
		
		//return "login success";
	}
	
	//this api need to be token validated
	@GetMapping(value="/info", produces = MediaType.TEXT_PLAIN_VALUE)
	public String info() {
		return LocalDateTime.now().toString();
	}
	
	//this page also need to be token validated
	//as logout page must be open for logged in user only
	//logged in user will always have token validated
	@DeleteMapping(value="/logout", produces = MediaType.TEXT_PLAIN_VALUE)
	public String logout(HttpServletRequest request) {
		service.deleteSession(request);
		return "logged out";
	}
}
