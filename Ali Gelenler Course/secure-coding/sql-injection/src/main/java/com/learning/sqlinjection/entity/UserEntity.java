package com.learning.sqlinjection.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.learning.weblogin.entity.User;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
//Data lombok annotation provided getters and setters that implemented interface User methods
public class UserEntity implements User {

	@Id
	@NotNull
	private BigInteger id;

	@NotNull
	@Column(name = "username", nullable = false)
	private String userName;

	@NotNull
	@Column(nullable = false)
	private String password;

	@NotNull
	private String name;

	@NotNull
	private String surname;
	
}
