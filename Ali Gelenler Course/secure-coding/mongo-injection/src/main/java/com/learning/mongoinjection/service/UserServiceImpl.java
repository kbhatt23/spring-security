package com.learning.mongoinjection.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.learning.mongoinjection.repository.UserRepository;
import com.learning.weblogin.entity.User;
import com.learning.weblogin.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> findUserByUserNameAndPassword(String username, String password) {
		log.info("Finding user with username: {} and password: {}", username, password);
		//Optional<User> user = userRepository.findUserByUserNameAndPassword(username, password);
		Optional<User> user = userRepository.findByUserNameAndPassword(username, password);
		
		user.ifPresent(value -> LOG.info("Returning user information for user {}", value.getUserName()));
		return user;
	}

	@Override
	public Optional<User> findUserByUserId(String userId) {
		log.info("Finding user info with id: {}", userId);
		Optional<User> user = userRepository.findUserByUserId(userId);
		
		//Optional<User> user = userRepository.findById(userId);
		user.ifPresent(value -> LOG.info("Returning user information for user {}", value.getUserName()));
		return user;
	}

	@Override
	public Optional<User> findUserInfoByUsername(String username) {
		log.info("Finding user info with username: {}", username);
		Optional<User> user = userRepository.findUserByUserName(username);
		user.ifPresent(value -> LOG.info("Returning user information for user {}", value.getUserName()));
		return user;
	}

}
