package com.learning.mvcwebapplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO {

	private int id;
	
	private String title;
	
	private String url;
}
