package com.learning.resourceserver.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.resourceserver.dtos.AlbumDTO;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/albums")
@Log4j2
public class AlbumsController {

	//@PreAuthorize("hasRole('developer')")
	@GetMapping
	public List<AlbumDTO> getAllAlbums(){
		log.info("getAllAlbums called");
		AlbumDTO album1 = AlbumDTO.builder()
				.id(1)
				.url("http://localhost:9000/albums/1")
				.title("album-1")
				.build();
			
		AlbumDTO album2 = AlbumDTO.builder()
					.id(2)
					.url("http://localhost:9000/albums/2")
					.title("album-2")
					.build();
			
		return Arrays.asList(album1,album2);
	}
}
