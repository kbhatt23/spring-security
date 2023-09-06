package com.learning.springsecurity.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//@Component
public class XSSResponseHeaderFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.setHeader("X-XSS-Protection", "0"); //browser security
		response.setHeader("X-Content-Type-Options", "nosniff"); //browser wont predict but use actual response content-type
		
		//allow browser to call our script only
		//but this will work only for src/resource/ scripts
		response.setHeader("Content-Security-Policy", "script-src 'self' 'nonce-someRand0mNonc3';");
	
		filterChain.doFilter(request, response);
	}

}
