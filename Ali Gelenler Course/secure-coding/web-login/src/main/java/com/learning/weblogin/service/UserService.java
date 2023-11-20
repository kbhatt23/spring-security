package com.learning.weblogin.service;

import java.util.Optional;

import com.learning.weblogin.entity.User;

public interface UserService {

	public Optional<User> findUserByUserNameAndPassword(String userName, String password);

	public Optional<User> findUserByUserId(String userId);

	default Optional<User> findUserInfoByUsername(String username) {
		return Optional.empty();
	}
}
