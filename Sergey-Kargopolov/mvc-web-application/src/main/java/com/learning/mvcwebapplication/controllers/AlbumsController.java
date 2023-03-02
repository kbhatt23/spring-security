package com.learning.mvcwebapplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learning.mvcwebapplication.dtos.AlbumDTO;
import com.learning.mvcwebapplication.services.AlbumsService;

@Controller
@RequestMapping("/albums")
public class AlbumsController {

	@Autowired
	private AlbumsService albumService;

	@GetMapping
	public String getAlbums(Model model, @AuthenticationPrincipal OidcUser oidcUser)
	// one way to get authentication is controller method invokation
	// , Authentication authentication) {
	{

		List<AlbumDTO> albums = albumService.findAllAlbums(oidcUser);

		model.addAttribute("albums", albums);

		return "albums";
	}

}
