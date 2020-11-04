package com.learning.springsecurity.helloworldsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//works only if @bean is there
//as in autocifniduration we hvae done conditionOnMissing bean and not missing class

//uncommenitng so can play wiht other config
//@Configuration
public class HTTPBasicSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/**/private/**")
				/* .anyRequest() */.authenticated()
			.and()
		//.formLogin().and()
		.httpBasic();
	}
}
