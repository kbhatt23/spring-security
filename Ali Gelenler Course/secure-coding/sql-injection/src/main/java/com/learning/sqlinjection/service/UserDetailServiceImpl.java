package com.learning.sqlinjection.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learning.sqlinjection.entity.UserDetailEntity;
import com.learning.sqlinjection.repository.UserDetailRepository;
import com.learning.weblogin.entity.UserDetail;
import com.learning.weblogin.service.UserDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailService {

	private final UserDetailRepository userDetailRepository;

	public UserDetailServiceImpl(UserDetailRepository userDetailRepository) {
		this.userDetailRepository = userDetailRepository;
	}

	@Override
	public void addUserDetail(UserDetail userDetail) {
		log.info("Adding user details for user id {}", userDetail.getId());
		userDetailRepository.save(getUserDetailEntity(userDetail));
	}

	@Override
	public Optional<? extends UserDetail> getUserDetailByUserId(BigInteger userId) {
		log.info("Reading user detail with user id {}", userId);
		return userDetailRepository.findByUserId(userId);
	}

	@Override
	public Optional<List<UserDetail>> getUserDetails() {
		log.info("Reading all user details");
		return Optional.of(new ArrayList<>(userDetailRepository.findAll()));
	}

	private UserDetailEntity getUserDetailEntity(UserDetail userDetail) {
		return UserDetailEntity.builder().id(userDetail.getId()).userId(userDetail.getUserId())
				.address(userDetail.getAddress()).creditCardNo(userDetail.getCreditCardNo())
				.comment(userDetail.getComment()).build();
	}
}
