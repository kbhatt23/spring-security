package com.learning.ldapinjection.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.ldapinjection.repository.UserRepository;
import com.learning.ldapinjection.repository.UserRoleRepository;
import com.learning.weblogin.entity.User;
import com.learning.weblogin.entity.UserRole;
import com.learning.weblogin.security.WebLoginUser;

@Service
public class LdapInjectionUserDetailsService implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(LdapInjectionUserDetailsService.class);

	private static final String ROLE_PREFIX = "ROLE_";

	private final UserRepository userRepository;

	private final UserRoleRepository userRoleRepository;

	public LdapInjectionUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findUserByUsername(username);

		if (userOptional.isEmpty()) {
			throw new UsernameNotFoundException("Username not found: " + username);
		}

		User user = userOptional.get();

		Optional<List<UserRole>> userRoles = userRoleRepository.findUserRoleByUserId(username);

		List<SimpleGrantedAuthority> grantedAuthorities = userRoles.map(userRoleList -> userRoleList.stream()
				.map(result -> new SimpleGrantedAuthority(ROLE_PREFIX + result.getRole())).collect(Collectors.toList()))
				.orElse(Collections.emptyList());

		if (grantedAuthorities.isEmpty()) {
			throw new UsernameNotFoundException("Username not found: " + username);
		}

		LOG.info("Loaded user by username {}", username);

		return WebLoginUser.builder().userId(user.getId()).userName(user.getUserName()).authorities(grantedAuthorities)
				.build();
	}
}
