package com.learning.springsecurity.dtos;

import java.util.List;

import lombok.Data;

@Data
public class HexColorPaginationResponse{

	private final List<HexColor> colors;
	private final int size;
	private final int page;
	private final int totalPages;
	
}
