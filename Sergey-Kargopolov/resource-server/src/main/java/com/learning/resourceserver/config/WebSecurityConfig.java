package com.learning.resourceserver.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.learning.resourceserver.security.KeycloakRoleConverter;

// Aim/Goal is to enable granular level security
// step 1: JWT based oauth 2 authorization for each endpoint
//step 2: Enforce scope 'users-sample' for all endpoints with get contract "/users"
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true , prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter(); 
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
		
		http
			.cors().and()
			.authorizeRequests()
				.antMatchers(HttpMethod.GET,"/users/sample")
				//.hasAuthority("SCOPE_profile") // actual scope name is profile
				 //next step is to check with custom scope as well
				//.hasAuthority("SCOPE_users-sample") // actual scope name is users-sample
				
				.hasAnyRole("developer")
				.anyRequest().authenticated()
				.and()
			.oauth2ResourceServer()
			.jwt()
			.jwtAuthenticationConverter(jwtAuthenticationConverter)
			;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		
		return source;
	}

}
