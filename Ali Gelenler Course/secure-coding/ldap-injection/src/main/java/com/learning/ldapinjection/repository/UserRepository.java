package com.learning.ldapinjection.repository;

import java.util.Optional;

import com.learning.weblogin.entity.User;

public interface UserRepository {

	Optional<User> findUserByUsernameAndPassword(String username, String password);

	Optional<User> findUserByUsername(String username);

	Optional<User> findUserByUserId(String userId);
}
