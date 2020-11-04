package com.learning.springsecurity.helloworldsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//works only if @bean is there
//as in autocifniduration we hvae done conditionOnMissing bean and not missing class
import org.springframework.security.crypto.password.PasswordEncoder;

//uncommenitng so can play wiht other config
//@Configuration
public class HTTPBasicSecurityConfigWithSeperateRoles extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/**/private/**").hasRole("ADMIN")
				/* .anyRequest() *//* .authenticated() */
		.and()
		.authorizeRequests()
			.antMatchers("/**/user/**").hasAnyRole("USER","ADMIN")
		//.formLogin().and()
		.and() 
		//for others no need of credentials
		.httpBasic();
	}
	
	//adding users
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("kbhatt23").password(passwordEncoder().encode("jaishreeram")).roles("USER")
			.and()
			.withUser("raghava").password(passwordEncoder().encode("jaishreeram")).roles("ADMIN");
			
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
