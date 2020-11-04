package com.learning.springsecurity.helloworldsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class FormBaedSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//since it goes to login.html from mvc lets allow login page
		    //.antMatchers("/h2-console/**","/login").permitAll()
			//ADDING CUSTOM LOGIN/LOGIN AND H2 CONSOLE TO PERMIT WIHT NO AUTHENTICATOIN
		    .antMatchers("/h2-console/**","/mylogin").permitAll()
		    .and()
		    .authorizeRequests()
			.antMatchers("/**/private/**").hasRole("ADMIN")
				/* .anyRequest() *//* .authenticated() */
		.and()
		.authorizeRequests()
		.antMatchers("/**/user/**").hasAnyRole("USER","ADMIN")
		.and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
        .and().headers().frameOptions().sameOrigin()//allow use of frame to same origin urls
        .and() 
        //form logic default url is /login
        .formLogin()
        //adding custom mylogin.html as login page
       // .loginPage("/mylogin").defaultSuccessUrl("/")
		    ;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/webjars/**");
	}
	
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
}
