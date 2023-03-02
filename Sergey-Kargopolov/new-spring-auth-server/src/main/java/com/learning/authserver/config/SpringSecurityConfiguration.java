package com.learning.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SpringSecurityConfiguration {

	private static final String PASSWORD = "sitaramalakshmanhanuman";
	private static final String USERNAME = "kbhatt23";

	@Bean
	SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
				.formLogin(Customizer.withDefaults());

		return http.build();

	}

	@Bean
	public UserDetailsService users() {

		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		UserDetails user = User.withUsername(USERNAME).password(encoder.encode(PASSWORD)).roles("USER").build();

		return new InMemoryUserDetailsManager(user);

	}
}
