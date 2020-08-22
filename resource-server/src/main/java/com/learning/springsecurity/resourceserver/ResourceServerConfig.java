package com.learning.springsecurity.resourceserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	//skipping below as this is moved to config
		//also for jwt we do not need this
		//@Bean
		//public RemoteTokenServices tokenService(){
			//RemoteTokenServices tokenService = new RemoteTokenServices();
			//tokenService.setCheckTokenEndpointUrl("http://localhost:9090/auth/oauth/check_token");
			//tokenService.setClientId("keshavaclient");
			//tokenService.setClientSecret("secret");
			
			//return tokenService;
		//}
	@Override 
    public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").fullyAuthenticated();
	
    }
	//fetching from applicaiton.proeprties
	@Autowired
	private RemoteTokenServices tokenService;
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		
	
		resources.tokenServices(tokenService);
		
		//resources.tokenStore(tokenStore());
		
	}
}
