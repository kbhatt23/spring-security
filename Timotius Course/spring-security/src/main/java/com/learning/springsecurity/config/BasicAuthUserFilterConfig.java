package com.learning.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learning.springsecurity.filters.BasicAuthUserFilter;
import com.learning.springsecurity.repository.BasicAuthUserRepository;

//@Configuration
public class BasicAuthUserFilterConfig {

	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;
	
	@Bean
	public FilterRegistrationBean<BasicAuthUserFilter> basicAuthUserFilter() {
		var basicAuthUserFilter = new FilterRegistrationBean<BasicAuthUserFilter>();

		basicAuthUserFilter.setFilter(new BasicAuthUserFilter(basicAuthUserRepository));
		basicAuthUserFilter.setName("basicAuthUserFilter");
		basicAuthUserFilter.addUrlPatterns("/auth/basic/time");

		return basicAuthUserFilter;
	}


}
