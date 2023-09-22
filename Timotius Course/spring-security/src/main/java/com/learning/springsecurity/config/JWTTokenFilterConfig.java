package com.learning.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learning.springsecurity.filters.JWTTokenFilter;
import com.learning.springsecurity.filters.JWTTokenLoginFilter;
import com.learning.springsecurity.repository.BasicAuthUserRepository;
import com.learning.springsecurity.services.JWTAuthService;

@Configuration
public class JWTTokenFilterConfig {

	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@Autowired
	private JWTAuthService jwtAuthService;

	@Bean
	public FilterRegistrationBean<JWTTokenLoginFilter> jwtTokenLoginFilter() {
		var registrationBean = new FilterRegistrationBean<JWTTokenLoginFilter>();

		registrationBean.setFilter(new JWTTokenLoginFilter(basicAuthUserRepository));
		registrationBean.addUrlPatterns("/auth/jwt/login");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<JWTTokenFilter> jwtTokenFilter() {
		var registrationBean = new FilterRegistrationBean<JWTTokenFilter>();

		registrationBean.setFilter(new JWTTokenFilter(jwtAuthService));
		registrationBean.addUrlPatterns("/auth/jwt/info");
		registrationBean.addUrlPatterns("/auth/jwt/logout");

		return registrationBean;
	}

}
