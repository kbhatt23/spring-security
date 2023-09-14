package com.learning.springsecurity.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHashCredentials {

	@Id
	private String userName;
	
	private String hashedPassword;
	
	private String salt;
}
