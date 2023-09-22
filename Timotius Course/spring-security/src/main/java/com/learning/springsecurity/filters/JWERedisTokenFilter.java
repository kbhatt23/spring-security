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

import com.learning.springsecurity.constants.JWTAuthConstants;
import com.learning.springsecurity.dtos.JWERedisDataToken;
import com.learning.springsecurity.services.JWERedisAuthService;
import com.learning.springsecurity.util.HTTPUtil;

public class JWERedisTokenFilter extends OncePerRequestFilter {

	private final JWERedisAuthService service;

	public JWERedisTokenFilter(JWERedisAuthService service) {
		this.service = service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (isVAlidJWE(request)) {
				filterChain.doFilter(request, response);
			} else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				PrintWriter writer = response.getWriter();
				writer.print("{\"message\":\"Invalid JWE token\"}");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isVAlidJWE(HttpServletRequest request) {
		var bearerToken = HTTPUtil.extractBearerToken(request);

		Optional<JWERedisDataToken> redisTokenOptional = service.read(bearerToken);

		boolean isValid =  redisTokenOptional.isPresent();
		if(isValid)
			request.setAttribute(JWTAuthConstants.JWT_ID, redisTokenOptional.get().getJwtId());
		
		return isValid;
	}

	

}
