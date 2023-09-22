package com.learning.springsecurity.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.learning.springsecurity.controllers.authentication.BasicAuthUserController;
import com.learning.springsecurity.entity.BasicAuthUser;
import com.learning.springsecurity.repository.BasicAuthUserRepository;
import com.learning.springsecurity.util.EncodeDecodeUtil;
import com.learning.springsecurity.util.EncryptDecryptUtil;
import com.learning.springsecurity.util.HashUtil;

//@Component
//first call authentication filter only
@Order(value = 1)
public class BasicACLAuthenticateUserFilter extends OncePerRequestFilter {

	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (isValidUsernameAndPassword(authHeader, request)) {
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			PrintWriter writer = response.getWriter();
			writer.print("{\"message\":\"Invalid Basic Authentication\"}");
		}

	}

	private boolean isValidUsernameAndPassword(String authHeader, HttpServletRequest request) {
		if (StringUtils.isBlank(authHeader)) {
			return false;
		}

		try {
			// Basic username:password
			String encodedUserPass = authHeader.trim().substring("Basic ".length());
			String userPass = EncodeDecodeUtil.decodeBase64(encodedUserPass);

			String[] split = userPass.split(":");
			String userName = split[0];
			String password = split[1];
			Optional<BasicAuthUser> findByUsername = basicAuthUserRepository.findByUsername(
					EncryptDecryptUtil.encryptAes(userName.toLowerCase(), BasicAuthUserController.SECRET));

			if (findByUsername.isPresent()) {
				BasicAuthUser basicAuthUser = findByUsername.get();

				if (HashUtil.isSha256Match(password, basicAuthUser.getSalt(), basicAuthUser.getPasswordHash())) {
					request.setAttribute("basicAuthUser", basicAuthUser);
					return true;
				} else {
					return false;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
