package com.learning.sqlinjection.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.learning.weblogin.validator.InputValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SQLInputValidator implements InputValidator {

	@Override
	public void valdiateUserName(String userName) {
		if (!StringUtils.hasLength(userName)) {
			log.info("Username is not valid!");
			throw new IllegalArgumentException("Username is not valid!");
		}
	}

	@Override
	public void valdiatePassword(String password) {
		if (!StringUtils.hasLength(password)) {
			log.info("Password is not valid!");
			throw new IllegalArgumentException("Password is not valid!");
		}
	}

	@Override
	public void valdiateUserId(String userId) {
		if (!StringUtils.hasLength(userId)) {
			log.info("UserId is not valid!");
			throw new IllegalArgumentException("UserId is not valid!");
		}
	}

}
