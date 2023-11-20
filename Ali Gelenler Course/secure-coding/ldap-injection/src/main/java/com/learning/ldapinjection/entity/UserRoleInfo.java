package com.learning.ldapinjection.entity;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import com.learning.weblogin.entity.UserRole;

import lombok.Data;

@Data
public class UserRoleInfo implements UserRole {

	@NotNull
	private BigInteger id;

	@NotNull
	private BigInteger userId;

	@NotNull
	private String role;
}
