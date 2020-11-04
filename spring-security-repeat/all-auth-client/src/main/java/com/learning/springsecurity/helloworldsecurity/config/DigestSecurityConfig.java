package com.learning.springsecurity.helloworldsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

//@Configuration
public class DigestSecurityConfig extends WebSecurityConfigurerAdapter {

	// Observe the we are injecting userDetailsService and
	// DigestAuthenticationEntryPoint

	private DigestAuthenticationFilter getDigestAuthFilter() throws Exception {
		DigestAuthenticationFilter digestFilter = new DigestAuthenticationFilter();
		digestFilter.setUserDetailsService(userDetailsServiceBean());
		digestFilter.setAuthenticationEntryPoint(getDigestEntryPoint());
		return digestFilter;
	}

	// TODO-5 uncomment the below to configure DigestAuthenticationEntryPoint

	private DigestAuthenticationEntryPoint getDigestEntryPoint() {
		DigestAuthenticationEntryPoint digestEntryPoint = new DigestAuthenticationEntryPoint();
		digestEntryPoint.setRealmName("raghava-realm");
		//key can be made differnt
		//this will be used to generate the nonce number sent as part of respones header while telling browser to open login popup
		digestEntryPoint.setKey("somedigestkey");
		return digestEntryPoint;
	}
	
	//method used to insert users
	//for demo using inmemory noop password encoder -> plain password with no encryption
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
					.withUser("kbhatt23").password(passwordEncoder().encode("jaishreeram")).roles("USER") // setting oly user rol for kbhatt23
					.and()
					.withUser("raghava").password(passwordEncoder().encode("jaishreeram")).roles("ADMIN");
	}
	//just for demo
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**/private/**").addFilter(getDigestAuthFilter()).exceptionHandling()
		.authenticationEntryPoint(getDigestEntryPoint()).and().authorizeRequests().antMatchers("/**/private/**")
		.hasRole("ADMIN")
		;
	}
}
