package com.learning.springsecurity.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

//@Configuration
//this do not support parsing hence using loggly
//loggly provide parsing as well as viewing in its ui
//could have used logstash + elastic + kibana but for simplicity using loggly 
//loggly can do parsing, and sending logs to data store + view in ui in one tool
public class AuditRequestLoggingFilter {

	@Bean
	public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
		CommonsRequestLoggingFilter commonsRequestLoggingFilter = new CommonsRequestLoggingFilter();
		commonsRequestLoggingFilter.setIncludeQueryString(true);
		commonsRequestLoggingFilter.setIncludePayload(true);
		commonsRequestLoggingFilter.setIncludeHeaders(true);
		
		return commonsRequestLoggingFilter;
	}
}
