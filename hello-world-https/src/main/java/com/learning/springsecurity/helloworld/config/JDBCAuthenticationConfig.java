package com.learning.springsecurity.helloworld.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class JDBCAuthenticationConfig extends WebSecurityConfigurerAdapter {
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/h2-console/**","/hello-world/**").permitAll()
            .antMatchers("/admin/**")
            .hasAnyRole("ADMIN")
            .anyRequest().authenticated()//all other urls can be access by any authenticated role
            .and().httpBasic()//enable form login instead of basic login
            .and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
            .and().headers().frameOptions().sameOrigin();//allow use of frame to same origin urls
    }
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication()
				.dataSource(dataSource);
	}
	
}
