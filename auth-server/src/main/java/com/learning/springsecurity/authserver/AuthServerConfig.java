package com.learning.springsecurity.authserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
@Order(2)
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	 @Autowired
	 private AuthenticationManager authenticationManager;

	 @Autowired
		private PasswordEncoder passwordEncoder;
	//method to add client data
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("kbhatt23client").authorizedGrantTypes("password", "authorization_code")
				.secret(passwordEncoder.encode("secret")).scopes("user_info").
				redirectUris("https://localhost:8444/sitaram-app/login/oauth2/code/keshavaclient")
				.autoApprove(false).and()

				.withClient("keshavaclient")
				.authorizedGrantTypes("password", "authorization_code", "client_credentials")
				.secret(passwordEncoder.encode("secret")).scopes("user_info","read")
				.redirectUris("https://localhost:8444/sitaram-app/login/oauth2/code/keshavaclient")
				.autoApprove(false);
	}
	//method to allow password type grant type
	//this is needed as password type grant type alos need username and passworf for authentication
	//after that only user is authorized
	//authenticationmanaer is created in websecurityconfigureradapter class
	 @Override
	  public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	      endpoints
	              .authenticationManager (authenticationManager)        
	              .tokenStore (new InMemoryTokenStore ());
	  }
	 //method to enable call of check_token api
	 @Override
	    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
	       
			oauthServer.tokenKeyAccess("isAuthenticated()")
	        	 .checkTokenAccess("isAuthenticated()")              
	            ;
	        
	    }
}
