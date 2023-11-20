package com.learning.mongoinjection.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.mongoinjection.collections.UserRoleDocument;
import com.learning.mongoinjection.repository.UserRepository;
import com.learning.mongoinjection.repository.UserRoleRepository;
import com.learning.weblogin.entity.User;
import com.learning.weblogin.security.WebLoginUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoSqlInjectionUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	private final UserRoleRepository userRoleRepository;

	private static final String ROLE_PREFIX = "ROLE_";

	public NoSqlInjectionUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

		List<UserRoleDocument> userRoles = userRoleRepository.findByUserId(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

		log.info("Loaded user by username {}", username);

		List<SimpleGrantedAuthority> authorities = userRoles.stream()
				.map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole())).collect(Collectors.toList());

		return WebLoginUser.builder().userId(user.getId()).userName(user.getUserName()).authorities(authorities)
				.build();
	}

}