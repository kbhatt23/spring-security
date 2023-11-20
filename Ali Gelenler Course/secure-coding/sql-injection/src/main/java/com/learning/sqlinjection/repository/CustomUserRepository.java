package com.learning.sqlinjection.repository;

import java.util.Optional;

import com.learning.weblogin.entity.User;

public interface CustomUserRepository {

    Optional<User> findUserByUserNameAndPassword(String userName, String password);

    Optional<User> findUserByUserName(String username);

    Optional<User> findUserByUserId(String userId);
}
