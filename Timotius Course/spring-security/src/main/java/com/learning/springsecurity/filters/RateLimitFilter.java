package com.learning.springsecurity.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimitFilter extends OncePerRequestFilter{
	
	private RateLimiter rateLimiter;
	
	public RateLimitFilter(double transactionsPerSeconds) {
		this.rateLimiter = RateLimiter.create(transactionsPerSeconds);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(rateLimiter.tryAcquire()) {
			filterChain.doFilter(request, response);
		}else {
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			response.setHeader(HttpHeaders.RETRY_AFTER, "5"); // info for the client he can implement retrying after 5 seconds
		}
	}

}
