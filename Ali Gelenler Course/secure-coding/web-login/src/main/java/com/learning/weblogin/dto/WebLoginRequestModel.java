package com.learning.weblogin.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebLoginRequestModel {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
}
