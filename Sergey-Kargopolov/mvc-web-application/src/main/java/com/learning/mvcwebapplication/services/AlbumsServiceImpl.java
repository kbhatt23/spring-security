package com.learning.mvcwebapplication.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.learning.mvcwebapplication.dtos.AlbumDTO;

@Service
public class AlbumsServiceImpl implements AlbumsService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SecurityUtils securityUtils;

	@Value("${albumServiceURL}")
	private String albumServiceURL;

	@Override
	public List<AlbumDTO> findAllAlbums(OidcUser oidcUser) {
		// JWT only but can not be used as bearer authorization
		String idToken = securityUtils.fetchIDToken(oidcUser);

		String JWTToken = securityUtils.fetchOauth2JWTToken();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + JWTToken);

		HttpEntity<List<AlbumDTO>> httpEntity = new HttpEntity<>(httpHeaders);

		ResponseEntity<List<AlbumDTO>> exchange = restTemplate.exchange(albumServiceURL, HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<List<AlbumDTO>>() {
				});

		return exchange.getBody();
	}

}
