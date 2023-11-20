package com.learning.ldapinjection.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.learning.weblogin.validator.InputValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LdapInputValidator implements InputValidator {

	@Override
	public void valdiateUserName(String username) {
		if (!StringUtils.hasLength(username)) {
			log.error("Username is not valid!");
			throw new IllegalArgumentException("Username is not valid!");
		}
	}

	@Override
	public void valdiatePassword(String password) {
		if (!StringUtils.hasLength(password)) {
			log.error("Password is not valid!");
			throw new IllegalArgumentException("Password is not valid!");
		}
	}

	@Override
	public void valdiateUserId(String userId) {
		if (!StringUtils.hasLength(userId)) {
			log.error("User id is not valid!");
			throw new IllegalArgumentException("User id is not valid!");
		}
	}
}
