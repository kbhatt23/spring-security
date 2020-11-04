package com.learning.spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	
	
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	//same user cna be used for password garnt type as well as authorization code grant type
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("shambhu").password(passwordEncoder.encode("jaishreeram"))
			.roles("USER");
	}
	
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	
	
	// TODO-9  uncomment the below method so that it matches /oauth/authorize and any request to /oauth/authorize to be authenticated.
	// we dont want security for login page. so, below configuration is permitting any requests to /login

	//below code helps in authorization code garnt
	//as we need to show login page
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	
		http
		.authorizeRequests()
		.antMatchers("/login").permitAll()
		
		.antMatchers("/oauth/authorize")
			.authenticated()
			.and().formLogin()
		.and().requestMatchers()
        	.antMatchers("/login","/oauth/authorize");	
			

	}
	
	
	
	
	
	
	

	
	
	
}