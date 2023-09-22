package com.learning.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learning.springsecurity.filters.JWTRedisTokenFilter;
import com.learning.springsecurity.filters.JWTRedisTokenLoginFilter;
import com.learning.springsecurity.repository.BasicAuthUserRepository;
import com.learning.springsecurity.services.JWTRedisAuthService;

//@Configuration
public class JWTRedisTokenFilterConfig {

	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@Autowired
	private JWTRedisAuthService jwtRedisAuthService;

	@Bean
	public FilterRegistrationBean<JWTRedisTokenLoginFilter> jwtRedisTokenLoginFilter() {
		var registrationBean = new FilterRegistrationBean<JWTRedisTokenLoginFilter>();

		registrationBean.setFilter(new JWTRedisTokenLoginFilter(basicAuthUserRepository));
		registrationBean.addUrlPatterns("/auth/jwt-redis/login");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<JWTRedisTokenFilter> jwtRedisTokenFilter() {
		var registrationBean = new FilterRegistrationBean<JWTRedisTokenFilter>();

		registrationBean.setFilter(new JWTRedisTokenFilter(jwtRedisAuthService));
		registrationBean.addUrlPatterns("/auth/jwt-redis/info");
		registrationBean.addUrlPatterns("/auth/jwt-redis/logout");

		return registrationBean;
	}

}
