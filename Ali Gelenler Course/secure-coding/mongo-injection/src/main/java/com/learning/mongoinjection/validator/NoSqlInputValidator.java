package com.learning.mongoinjection.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.learning.weblogin.validator.InputValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NoSqlInputValidator implements InputValidator {
	
	private static final String[] BLACKLIST = {"&&", "||", "\"" , "regex"};
	
	@Override
	public void valdiateUserName(String userName) {
		if (!StringUtils.hasLength(userName) /* || isBlacklisted(userName) */) {
			log.info("Username is not valid!");
			throw new IllegalArgumentException("Username is not valid!");
		}
	}

	@Override
	public void valdiatePassword(String password) {
		if (!StringUtils.hasLength(password) /* || isBlacklisted(password) */) {
			log.info("Password is not valid!");
			throw new IllegalArgumentException("Password is not valid!");
		}
	}

	@Override
	public void valdiateUserId(String userId) {
		if (!StringUtils.hasLength(userId) /* || isBlacklisted(userId) */) {
			log.info("UserId is not valid!");
			throw new IllegalArgumentException("UserId is not valid!");
		}
	}
	
	private boolean isBlacklisted(String input) {
		for(String blackListedSequence : BLACKLIST) {
			if(input.contains(blackListedSequence))
				return true;
		}
		
		return false;
	}
}
