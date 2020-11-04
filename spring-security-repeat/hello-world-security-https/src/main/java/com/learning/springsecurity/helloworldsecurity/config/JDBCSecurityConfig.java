package com.learning.springsecurity.helloworldsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class JDBCSecurityConfig extends WebSecurityConfigurerAdapter{
	//very helpfyul
	//based on the data.sql password prefix it will use encoder
	//in in sql it starts with noop it uses noop encoder and so on
	@Bean
	public PasswordEncoder passwordEncoder() {
		DelegatingPasswordEncoder passEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return passEncoder;
	}
	
	@Autowired
	private DataSource dataSource;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder())
			;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/**/private/**").hasRole("ADMIN")
				/* .anyRequest() *//* .authenticated() */
		.and()
		.authorizeRequests()
		.antMatchers("/h2-console/**").permitAll()
		.and()
		.authorizeRequests()
			.antMatchers("/**/user/**").hasAnyRole("USER","ADMIN")
		//.formLogin().and()
		.and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
        .and().headers().frameOptions().sameOrigin()//allow use of frame to same origin urls
        .and() 
		//for others no need of credentials
		.httpBasic()
		;
	}
}
