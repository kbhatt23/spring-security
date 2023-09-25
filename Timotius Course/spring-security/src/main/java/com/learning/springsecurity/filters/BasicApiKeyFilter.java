package com.learning.springsecurity.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.learning.springsecurity.constants.BasicApiAuthConstants;
import com.learning.springsecurity.controllers.authentication.BasicAuthUserController;
import com.learning.springsecurity.entity.BasicApiKey;
import com.learning.springsecurity.entity.BasicAuthUser;
import com.learning.springsecurity.repository.BasicApiKeyRepository;
import com.learning.springsecurity.repository.BasicAuthUserRepository;
import com.learning.springsecurity.util.EncodeDecodeUtil;
import com.learning.springsecurity.util.EncryptDecryptUtil;
import com.learning.springsecurity.util.HashUtil;

public class BasicApiKeyFilter extends OncePerRequestFilter {

	private final BasicApiKeyRepository basicApiKeyRepository;

	public BasicApiKeyFilter(BasicApiKeyRepository basicApiKeyRepository) {
		this.basicApiKeyRepository = basicApiKeyRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String apiKey = request.getHeader("X-ApiKey");

		if (isValidAPIKey(apiKey , request)) {
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			PrintWriter writer = response.getWriter();
			writer.print("{\"message\":\"Invalid Basic API Key\"}");
		}

	}

	private boolean isValidAPIKey(String apiKey,  HttpServletRequest request) {
		
		if(StringUtils.isBlank(apiKey)){
			return false;
		}
		
		try {
			Optional<BasicApiKey> findByApiKeyAndExpiredAtAfter = basicApiKeyRepository.findByApiKeyAndExpiredAtAfter(apiKey, LocalDateTime.now());
			
			
			if(findByApiKeyAndExpiredAtAfter.isPresent()) {
				request.setAttribute(BasicApiAuthConstants.REQUEST_ATTRIBUTE_USERNAME, findByApiKeyAndExpiredAtAfter.get().getUserId());
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
