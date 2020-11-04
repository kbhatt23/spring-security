	package com.learning.spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;


@Configuration
// Uncomment the below annotation to enable the configurations for Oauth Server endpoints 
//and also their security Configurations
@EnableAuthorizationServer

//here we can add clietn details like clientid, secret,redirect url, scopes of resource server, grant types
//this then exposes endpoints to get access tokenm and validate the toekn etc
// Make the below call to extend AuthorizationServerConfigurerAdapter
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter 
{

	
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("kbhatt23")
				//do not have garnt type as client_credentials
				.authorizedGrantTypes("password","authorization_code")
				.secret(encoder().encode("jaishreeram"))
				//in resource server we will define endpoints as part of this scope
				.scopes("user_info","read")
				.redirectUris("http://localhost:8999/sitaram-app/login/oauth2/code/customAuthClient")
				.autoApprove(false)
				.and()
				.withClient("radha-krishna")
				.authorizedGrantTypes("password","authorization_code","client_credentials")
				.secret(encoder().encode("jaishreeram"))
				.scopes("user_info","read")
				.redirectUris("http://localhost:8999/myapp/sitaram-app/oauth2/code/customAuthClient")
				.autoApprove(false)
							;
	}
	
	
	
	
	
	// TODO-5 uncomment the below to inject authentication manager into authorization server endpoints
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
		
		// TODO-8 uncomment the below to inject tokenstore() and accessTokenConverter() into AuthorizationServerEndpointsConfigurer
		
			.tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter());
		;
	}
	
	
	
	

	// TODO-6 uncomment the below method to permit all requests to check token.
	// Now restart the application , get a new token and try to check token

	
	//below is added to allow check_token api open for authenticated users
	@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
       
		oauthServer
        	 .checkTokenAccess("isAuthenticated()")  
        	 //the below url provides access of public key
        	 //when using resource server , the resoruce server shodul get the public key of auth server so that it can decrpt the signature of JWT
        	 .tokenKeyAccess("isAuthenticated()")
            ;
        
    }
    
    
    
	
	

	
	

	
	
	//TODO-7  Uncomment the below to enable JWT tokens
	
	
	 @Bean
	    public TokenStore tokenStore() {
	        return new JwtTokenStore(accessTokenConverter());
	    }

	    @Bean
	    public JwtAccessTokenConverter accessTokenConverter() {
	        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	        //converter.setSigningKey("123");
	        final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
	        		new ClassPathResource("mykeystore.jks"), "jaishreeram".toCharArray());
	         converter.setKeyPair(keyStoreKeyFactory.getKeyPair("tomcat"));
	        return converter;
	    }
	    
	    
	
	@Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
	
}
