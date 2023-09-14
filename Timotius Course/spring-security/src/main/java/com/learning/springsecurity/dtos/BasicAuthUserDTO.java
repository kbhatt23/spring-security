package com.learning.springsecurity.dtos;

import lombok.Data;

@Data
public class BasicAuthUserDTO {

	//plain username and password
	private String username;
	
	private String password;
	
	private String displayName;
}
