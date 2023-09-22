package com.learning.springsecurity.filters;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalCorsFilter extends CorsFilter {
	
	private static final List<String> allowedOrigins =  List.of("http://127.0.0.1:7001","http://192.168.29.116:7001");

	public GlobalCorsFilter() {
		super(corsConfiguration());
	}

	private static UrlBasedCorsConfigurationSource corsConfiguration() {
		var config = new CorsConfiguration();
		
		//to enable session cookie, tls crendentials and basic auth credentials
		config.setAllowCredentials(true);
		
		allowedOrigins.forEach(orign -> config.addAllowedOrigin(orign));
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}

}