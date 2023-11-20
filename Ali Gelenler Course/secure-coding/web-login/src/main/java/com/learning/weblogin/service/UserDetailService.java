package com.learning.weblogin.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.learning.weblogin.entity.UserDetail;

public interface UserDetailService {

    void addUserDetail(UserDetail userDetail);

    Optional<? extends UserDetail> getUserDetailByUserId(BigInteger userId);

    Optional<List<UserDetail>> getUserDetails();
}
