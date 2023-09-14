package com.learning.springsecurity.filters;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.springsecurity.dtos.AuditLoggerEvent;

@Component
public class LogglyAuditLogFilter extends OncePerRequestFilter{
	
	private static final Logger LOG = LoggerFactory.getLogger(LogglyAuditLogFilter.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	//private static ExecutorService executor = Executors.newSingleThreadExecutor();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		filterChain.doFilter(request, response);
		
		//executor.execute(() -> appendLog(request, response));
		
		appendLog(request, response);
	}

	private void appendLog(HttpServletRequest request, HttpServletResponse response) {
		AuditLoggerEvent auditLoggerEvent = new AuditLoggerEvent();
		
		try {
			auditLoggerEvent.setHeaders(request.getHeader(HttpHeaders.AUTHORIZATION));
			auditLoggerEvent.setMethod(request.getMethod());
			auditLoggerEvent.setPath(request.getRequestURI());
			auditLoggerEvent.setQuery(request.getQueryString());
			//auditLoggerEvent.setRequestBody(getRequestBody(request));
			
			auditLoggerEvent.setRequestBody("random request");
			auditLoggerEvent.setResponseStatusCode(response.getStatus());
			auditLoggerEvent.setTimestamp(LocalDateTime.now().toString());
			String logString = objectMapper.writeValueAsString(auditLoggerEvent);
			LOG.info(logString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 private String getRequestBody(HttpServletRequest request) throws IOException {
	        InputStream inputStream = request.getInputStream();
	        byte[] requestBodyBytes = StreamUtils.copyToByteArray(inputStream);
	        return new String(requestBodyBytes, request.getCharacterEncoding());
	    }

}
