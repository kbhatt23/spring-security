package com.learning.springsecurity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHashDetails {

	private String userName;
	
	private String password;
}
