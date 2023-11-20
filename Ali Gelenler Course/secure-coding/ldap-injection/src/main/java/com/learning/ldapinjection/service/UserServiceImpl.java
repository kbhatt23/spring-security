package com.learning.ldapinjection.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.learning.ldapinjection.repository.UserRepository;
import com.learning.weblogin.entity.User;
import com.learning.weblogin.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> findUserByUserNameAndPassword(String username, String password) {
		LOG.info("Finding user with username: {} and password: {}", username, password);
		Optional<User> user = userRepository.findUserByUsernameAndPassword(username, password);
		user.ifPresent(value -> LOG.info("Returning user information for user {}", value.getUserName()));
		return user;
	}

	@Override
	public Optional<User> findUserByUserId(String userId) {
		LOG.info("Finding user info with id: {}", userId);
		Optional<User> user = userRepository.findUserByUserId(userId);
		user.ifPresent(value -> LOG.info("Returning user information for user {}", value.getUserName()));
		return user;
	}
}
