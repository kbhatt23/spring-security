package com.learning.springsecurity.dtos;

import lombok.Data;

@Data
public class JWTRedisDataToken {

	//will store encrypted format
	private String userName;
	
	private String otherField;
	
	//for read/logout part
	private String jwtId;
	
	//other fields too
}
