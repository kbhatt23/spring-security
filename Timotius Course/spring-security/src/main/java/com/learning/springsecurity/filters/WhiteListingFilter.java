package com.learning.springsecurity.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//was used in airtel
//every one is by default blocked
//unless added in the whitelisted ip set
//@Component
public class WhiteListingFilter extends OncePerRequestFilter {

	private static final Set<String> WHITELISTED_IPS = Set.of("123.343.223.1211.1212", "0:0:0:0:0:0:0:1");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (WHITELISTED_IPS.contains(request.getRemoteAddr())) {
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());

			response.setContentType(MediaType.TEXT_PLAIN_VALUE);
			PrintWriter writer = response.getWriter();
			writer.print("Forbidden IP : " + request.getRemoteAddr());
		}
	}

}
