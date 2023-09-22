package com.learning.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learning.springsecurity.filters.JWERedisTokenFilter;
import com.learning.springsecurity.filters.JWERedisTokenLoginFilter;
import com.learning.springsecurity.repository.BasicAuthUserRepository;
import com.learning.springsecurity.services.JWERedisAuthService;

@Configuration
public class JWERedisTokenFilterConfig {

	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@Autowired
	private JWERedisAuthService jweRedisAuthService;

	@Bean
	public FilterRegistrationBean<JWERedisTokenLoginFilter> jweRedisTokenLoginFilter() {
		var registrationBean = new FilterRegistrationBean<JWERedisTokenLoginFilter>();

		registrationBean.setFilter(new JWERedisTokenLoginFilter(basicAuthUserRepository));
		registrationBean.addUrlPatterns("/auth/jwe-redis/login");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<JWERedisTokenFilter> jweRedisTokenFilter() {
		var registrationBean = new FilterRegistrationBean<JWERedisTokenFilter>();

		registrationBean.setFilter(new JWERedisTokenFilter(jweRedisAuthService));
		registrationBean.addUrlPatterns("/auth/jwe-redis/info");
		registrationBean.addUrlPatterns("/auth/jwe-redis/logout");

		return registrationBean;
	}

}
