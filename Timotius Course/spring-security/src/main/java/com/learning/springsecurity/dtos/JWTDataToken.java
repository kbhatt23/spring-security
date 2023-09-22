package com.learning.springsecurity.dtos;

import lombok.Data;

@Data
public class JWTDataToken {

	//will store encrypted format
	private String userName;
	
	private String otherField;
	
	//other fields too
}
