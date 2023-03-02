package com.learning.mvcwebapplication.services;

import java.util.List;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.learning.mvcwebapplication.dtos.AlbumDTO;

public interface AlbumsService {

	public List<AlbumDTO> findAllAlbums(OidcUser oidcUser);
}
