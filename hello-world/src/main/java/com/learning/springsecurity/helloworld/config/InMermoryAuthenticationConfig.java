package com.learning.springsecurity.helloworld.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

//@Configuration
//@Order(1)
public class InMermoryAuthenticationConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserBuilder users = User.withDefaultPasswordEncoder();
		auth.inMemoryAuthentication()
				.withUser(users.username("kbhatt23").password("jaishreeram").roles("EMPLOYEE"))
				.withUser(users.username("kanha").password("jaishreeram").roles("ADMIN"))
				;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/hello-world/**")
		.permitAll()
			.antMatchers("/admin/**")
			.hasRole("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}
	
}
