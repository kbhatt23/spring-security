package com.learning.weblogin.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.learning.weblogin.entity.User;
import com.learning.weblogin.service.UserService;
import com.learning.weblogin.validator.InputValidator;

@Service
public class WebLoginAuthenticationProvider implements AuthenticationProvider {
	
	private final UserService userService;
	
    private final UserDetailsService userDetailsService;
    
    private final InputValidator inputValidator;
    
	public WebLoginAuthenticationProvider(UserService userService, UserDetailsService userDetailsService,
			InputValidator inputValidator) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
		this.inputValidator = inputValidator;
	}

	//authentication object is created by spring to populate the data from front end like username and password
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String password = (String)authentication.getCredentials();
		
		inputValidator.valdiateUserName(userName);
		inputValidator.valdiatePassword(password);
		
		User user = userService.findUserByUserNameAndPassword(userName, password)
				  .orElseThrow(() ->  new BadCredentialsException("Invalid username or password!"));
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
		
		return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authenticationClass) {
		return authenticationClass.equals(UsernamePasswordAuthenticationToken.class);
	}

}
