package com.learning.mongoinjection.collections;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.learning.weblogin.entity.User;

import lombok.Data;

@Data
@Document(collection = "users")
public class UserDocument implements User {

	@Id
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
