package com.learning.springsecurity.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

public class HTTPUtil {
	private HTTPUtil() {
	}

	public static final String extractBearerToken(HttpServletRequest request) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(StringUtils.isBlank(authHeader))
			return StringUtils.EMPTY;
		
		var bearerToken = authHeader.trim().substring("Bearer ".length());
		return bearerToken;
	}
}
