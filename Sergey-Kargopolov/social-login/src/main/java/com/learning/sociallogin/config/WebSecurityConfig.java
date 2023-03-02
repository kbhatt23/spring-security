package com.learning.sociallogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll() //allow index page open for all
		.anyRequest().authenticated().and().oauth2Login()
		.and()
		.logout() //enable springboot logout api
		//.logoutSuccessUrl("/")//set logout success url , however for external auth provider external session is not removed
		//for removal of external session of auth provider
		//we need spring config to call openid end session endpoint url that is exposed by external auth provider
		//logout handler do the same
		.logoutSuccessHandler(oidcLogoutSuccessHandler())
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.deleteCookies("JSESSIONID")
		; //except all other page should be authenticated
	}
    private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() { 
        OidcClientInitiatedLogoutSuccessHandler successHandler =
        		new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("http://localhost:9001/");
        return successHandler;
    }
	
}
