package com.learning.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learning.springsecurity.filters.BasicAuthSessionCookieFilter;
import com.learning.springsecurity.filters.BasicTokenSessionCookieFilter;
import com.learning.springsecurity.repository.BasicAuthUserRepository;
import com.learning.springsecurity.services.SessionCookieService;

//@Configuration
public class BasicAuthSessionCookieFilterConfig {
	
	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;
	

	@Autowired
	private SessionCookieService sessionCookieService;

	@Bean
	public FilterRegistrationBean<BasicAuthSessionCookieFilter> basicAuthSessionCookieFilter() {
		var registrationBean = new FilterRegistrationBean<BasicAuthSessionCookieFilter>();

		registrationBean.setFilter(new BasicAuthSessionCookieFilter(basicAuthUserRepository));
		registrationBean.addUrlPatterns("/auth/session-cookie/login");

		return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<BasicTokenSessionCookieFilter> basicTokenSessionCookieFilter() {
		var registrationBean = new FilterRegistrationBean<BasicTokenSessionCookieFilter>();

		registrationBean.setFilter(new BasicTokenSessionCookieFilter(sessionCookieService));
		registrationBean.addUrlPatterns("/auth/session-cookie/info");
		registrationBean.addUrlPatterns("/auth/session-cookie/logout");
		

		return registrationBean;
	}
	
}
