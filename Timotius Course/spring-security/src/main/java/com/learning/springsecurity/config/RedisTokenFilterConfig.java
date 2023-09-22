package com.learning.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learning.springsecurity.filters.RedisSessionTokenLoginFilter;
import com.learning.springsecurity.filters.RedisTokenFilter;
import com.learning.springsecurity.repository.BasicAuthUserRepository;
import com.learning.springsecurity.services.RedisTokenService;

//@Configuration
public class RedisTokenFilterConfig {

	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@Autowired
	private RedisTokenService redisTokenService;

	@Bean
	public FilterRegistrationBean<RedisSessionTokenLoginFilter> redisSessionTokenLoginFilter() {
		var registrationBean = new FilterRegistrationBean<RedisSessionTokenLoginFilter>();

		registrationBean.setFilter(new RedisSessionTokenLoginFilter(basicAuthUserRepository));
		registrationBean.addUrlPatterns("/auth/redis/login");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<RedisTokenFilter> redisTokenFilter() {
		var registrationBean = new FilterRegistrationBean<RedisTokenFilter>();

		registrationBean.setFilter(new RedisTokenFilter(redisTokenService));
		registrationBean.addUrlPatterns("/auth/redis/info");
		registrationBean.addUrlPatterns("/auth/redis/logout");

		return registrationBean;
	}

}
