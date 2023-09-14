package com.learning.springsecurity.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.springsecurity.controllers.hmac.HmacController;
import com.learning.springsecurity.dtos.HmacRequest;
import com.learning.springsecurity.services.HmacNonceStorage;
import com.learning.springsecurity.util.HmacUtil;

public class HmacFilter extends OncePerRequestFilter{
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var nonce = request.getHeader("X-Nonce");

		try {
			if (isValidHmac(request, nonce) && HmacNonceStorage.addWhenNotExists(nonce)) {
				filterChain.doFilter(request, response);
			} else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				PrintWriter writer = response.getWriter();
				writer.print("{\"message\":\"HMAC signature invalid\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isValidHmac(HttpServletRequest httpRequest, String nonce) throws Exception {
		var requestBody = objectMapper.readValue(httpRequest.getReader(), HmacRequest.class);
		var xRegisterDate = httpRequest.getHeader("X-Register-Date");
		var hmacFromClient = httpRequest.getHeader("X-Hmac");
		var hmacMessage = HmacUtil.constructHmacMessage(httpRequest.getMethod(), httpRequest.getRequestURI(),
				requestBody.getAmount(), requestBody.getFullName(), xRegisterDate, nonce);

		return HmacUtil.validateHmacToken(hmacMessage, HmacController.SECRET_KEY, hmacFromClient);
	}

}
