package com.learning.mongoinjection.repository;

import java.util.Optional;

import com.learning.weblogin.entity.User;

public interface CustomUserRepository {

    Optional<User> findUserByUserNameAndPassword(String username, String password);

    Optional<User> findUserByUserName(String username);

    Optional<User> findUserByUserId(String userId);

}
