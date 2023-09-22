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

import com.learning.springsecurity.constants.RedisAuthConstants;
import com.learning.springsecurity.dtos.RedisToken;
import com.learning.springsecurity.services.RedisTokenService;
import com.learning.springsecurity.util.HTTPUtil;

@RestController
@RequestMapping("/auth/redis")

public class RedisAuthenticationController {
	
	@Autowired
	private RedisTokenService service;

	//we do badic auth only for login to save cpu time in hash validation
	@PostMapping(value="/login", produces = MediaType.TEXT_PLAIN_VALUE)
	public String login(HttpServletRequest request){
		//login actual implementation
		//set the session cookie only if login was successful
		
		String encryptedUserName = (String)request.getAttribute(RedisAuthConstants.REQUEST_ATTRIBUTE_USERNAME);
		RedisToken token = new RedisToken();
		token.setUserName(encryptedUserName);
		return service.store(token);
		
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
		service.delete(HTTPUtil.extractBearerToken(request));
		return "logged out";
	}
}
