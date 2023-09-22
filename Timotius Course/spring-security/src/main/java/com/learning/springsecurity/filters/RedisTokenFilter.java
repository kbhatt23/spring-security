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

import com.learning.springsecurity.dtos.RedisToken;
import com.learning.springsecurity.services.RedisTokenService;
import com.learning.springsecurity.util.HTTPUtil;

public class RedisTokenFilter extends OncePerRequestFilter {

	private final RedisTokenService service;

	public RedisTokenFilter(RedisTokenService service) {
		this.service = service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (isValidRedisSession(request)) {
				filterChain.doFilter(request, response);
			} else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				PrintWriter writer = response.getWriter();
				writer.print("{\"message\":\"Invalid Redis token\"}");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isValidRedisSession(HttpServletRequest request) {
		var bearerToken = HTTPUtil.extractBearerToken(request);

		Optional<RedisToken> redisTokenOptional = service.read(bearerToken);

		return redisTokenOptional.isPresent();
	}

}
