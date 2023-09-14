package com.learning.springsecurity.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learning.springsecurity.filters.HmacFilter;


@Configuration
public class HmacFilterConfig {

	@Bean
	public FilterRegistrationBean<com.learning.springsecurity.filters.HmacFilter> hmacFilter() {
		var registrationBean = new FilterRegistrationBean<HmacFilter>();

		registrationBean.setFilter(new HmacFilter());
		registrationBean.addUrlPatterns("/hmac/info");

		return registrationBean;
	}

}
