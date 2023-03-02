package com.learning.mvcwebapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SecurityUtils {
	@Autowired
	private OAuth2AuthorizedClientService oaut2ClientService;

	public String fetchOauth2JWTToken() {
		// second way to get authentication object
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient loadAuthorizedClient = oaut2ClientService
				.loadAuthorizedClient(oauth2Token.getAuthorizedClientRegistrationId(), oauth2Token.getName());
		String oauthToken = loadAuthorizedClient.getAccessToken().getTokenValue();
		log.info("fetchOauth2JWTToken: oauth2 JWT token: {}", oauthToken);
		return oauthToken;
	}

	public String fetchIDToken(OidcUser oidcUser) {
		log.info("fetchIDToken: oidcUser: {}", oidcUser);
		String tokenValue = oidcUser.getIdToken().getTokenValue();
		log.info("fetchIDToken: tokenValue: {}", tokenValue);
		return tokenValue;
	}
}
