package com.learning.ldapinjection.entity;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import com.learning.weblogin.entity.User;

import lombok.Data;

@Data
public class UserInfo implements User {

	@NotNull
	private BigInteger id;

	@NotNull
	private String userName;

	@NotNull
	private String password;

	@NotNull
	private String name;

	@NotNull
	private String surname;
}
