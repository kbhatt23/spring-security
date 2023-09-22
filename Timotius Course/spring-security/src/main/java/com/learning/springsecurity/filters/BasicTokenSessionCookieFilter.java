package com.learning.springsecurity.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.learning.springsecurity.dtos.SessionCookieToken;
import com.learning.springsecurity.services.SessionCookieService;

public class BasicTokenSessionCookieFilter extends OncePerRequestFilter {

	private final SessionCookieService service;

	public BasicTokenSessionCookieFilter(SessionCookieService service) {
		this.service = service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (isValidSessionCookie(request)) {
				filterChain.doFilter(request, response);
			} else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				PrintWriter writer = response.getWriter();
				writer.print("{\"message\":\"Invalid token\"}");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isValidSessionCookie(HttpServletRequest request) {
		var csrfToken = request.getHeader("X-CSRF");

		Optional<SessionCookieToken> cookieTokenOptional = service.read(request, csrfToken);
		
		return cookieTokenOptional.isPresent();
	}

}
