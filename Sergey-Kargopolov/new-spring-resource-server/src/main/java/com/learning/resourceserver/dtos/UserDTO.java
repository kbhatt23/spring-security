package com.learning.resourceserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserDTO {

	private String id;
	
	private String name;
	
	private Integer age;
}
