package com.learning.ldapinjection.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learning.weblogin.entity.UserDetail;
import com.learning.weblogin.service.UserDetailService;

@Service
public class UserDetailServiceImpl implements UserDetailService {

	@Override
	public void addUserDetail(UserDetail userDetail) {

	}

	@Override
	public Optional<? extends UserDetail> getUserDetailByUserId(BigInteger userId) {
		return Optional.empty();
	}

	@Override
	public Optional<List<UserDetail>> getUserDetails() {
		return Optional.empty();
	}

}
