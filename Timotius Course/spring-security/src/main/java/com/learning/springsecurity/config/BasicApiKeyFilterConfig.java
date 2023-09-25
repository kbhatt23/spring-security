package com.learning.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learning.springsecurity.filters.BasicApiKeyFilter;
import com.learning.springsecurity.filters.HmacFilter;
import com.learning.springsecurity.repository.BasicApiKeyRepository;


//@Configuration
public class BasicApiKeyFilterConfig {

	@Autowired
	private BasicApiKeyRepository repository;
	
	@Bean
	public FilterRegistrationBean<BasicApiKeyFilter> basicApiKeyFilter() {
		var registrationBean = new FilterRegistrationBean<BasicApiKeyFilter>();

		registrationBean.setFilter(new BasicApiKeyFilter(repository));
		registrationBean.addUrlPatterns("/auth/apikey/time");

		return registrationBean;
	}

}
