package com.learning.sampleapp.interceptors;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.learning.sampleapp.constants.SampleApplicationConstants;

@Component
public class APIInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		request.setAttribute(SampleApplicationConstants.TRACE_ID, UUID.randomUUID().toString());
		return true;
	}
}
