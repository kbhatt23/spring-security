package com.learning.springsecurity.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learning.springsecurity.filters.RateLimitFilter;

//@Configuration
public class RateLimitFilterConfig {

	@Bean
	public FilterRegistrationBean<RateLimitFilter> rateLimitFilterBlue() {
		var registrationBean = new FilterRegistrationBean<RateLimitFilter>();

		registrationBean.setFilter(new RateLimitFilter(3));
		registrationBean.setName("rateLimitFilterBlue");
		registrationBean.addUrlPatterns("/dos/blue");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<RateLimitFilter> rateLimitFilterRed() {
		var registrationBean = new FilterRegistrationBean<RateLimitFilter>();

		registrationBean.setFilter(new RateLimitFilter(2));
		registrationBean.setName("rateLimitFilterRed");
		registrationBean.addUrlPatterns("/dos/blue");

		return registrationBean;
	}

}
