package com.learning.springsecurity.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.learning.springsecurity.entity.BasicAclUri;
import com.learning.springsecurity.entity.BasicAuthUser;
import com.learning.springsecurity.repository.BasicAclUriRepository;

//@Component
@Order(value = 2)
public class BasicACLAuthorizeUserFilter extends OncePerRequestFilter {

	@Autowired
	private BasicAclUriRepository basicAclUriRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (isACLAllowedForUser(request)) {
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			PrintWriter writer = response.getWriter();
			writer.print("{\"message\":\"Authorization Failed\"}");
		}

	}

	private boolean isACLAllowedForUser(HttpServletRequest request) {

		if (request == null || request.getAttribute("basicAuthUser") == null)
			return false;

		Object basicAuthUserObj = request.getAttribute("basicAuthUser");

		if (basicAuthUserObj instanceof BasicAuthUser) {
			BasicAuthUser basicAuthUser = (BasicAuthUser) basicAuthUserObj;
			
			
			Optional<BasicAclUri> basicACLURIOptional = basicAclUriRepository.findByUriAndMethod(request.getRequestURI(), request.getMethod());
			if(basicACLURIOptional.isEmpty()) {
				return true; // no validation needed
			}
			
			BasicAclUri basicAclUri= basicACLURIOptional.get();
			
			return basicAclUri.getUsers()
			      .stream()
			      .anyMatch(uri -> uri.getUserId().equals(basicAuthUser.getUserId()));
			
			//approach 2 maybe not better
			//in case acl is not set then it should be free for all
			//List<BasicAclUri> aclUris = basicAuthUser.getAclUris();
//			return aclUris.stream()
//					.anyMatch(basicAclURI -> basicAclURI.getMethod().equalsIgnoreCase(request.getMethod())
//							&& basicAclURI.getUri().contains(request.getRequestURI()));
		}

		return false;
	}

}
