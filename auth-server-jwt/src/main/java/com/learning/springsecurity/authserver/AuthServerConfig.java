package com.learning.springsecurity.authserver;

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
@EnableAuthorizationServer

// TODO-2 Make the below call to extend AuthorizationServerConfigurerAdapter
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter 
{

	@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
       
		oauthServer.tokenKeyAccess("isAuthenticated()")
        	 .checkTokenAccess("isAuthenticated()")              
            ;
        
    }
    
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("kbhatt23client").authorizedGrantTypes("password", "authorization_code")
				.secret(encoder().encode("secret")).scopes("user_info").autoApprove(false).and()

				.withClient("keshavaclient")
				.authorizedGrantTypes("password", "authorization_code", "client_credentials")
				.secret(encoder().encode("secret")).scopes("user_info").autoApprove(false);
	}
	
	
	// TODO-4 uncomment the below to inject authentication manager into authorization server endpoints
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
		
		// TODO-7 uncomment the below to inject tokenstore() and accessTokenConverter() into AuthorizationServerEndpointsConfigurer
		
			.tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter());
		;
	}
	
	
	//TODO-6  Uncomment the below to enable JWT tokens
	
	
	
	 @Bean
	    public TokenStore tokenStore() {
	        return new JwtTokenStore(accessTokenConverter());
	    }

	    @Bean
	    public JwtAccessTokenConverter accessTokenConverter() {
	        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	        //converter.setSigningKey("123");
	        final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
	        		new ClassPathResource("mykeystore.jks"), "password".toCharArray());
	         converter.setKeyPair(keyStoreKeyFactory.getKeyPair("tomcat"));
	        return converter;
	    }
	    
	@Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
	
}
