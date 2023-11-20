package com.learning.mongoinjection.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.mongoinjection.collections.UserDocument;
import com.learning.weblogin.entity.User;

public interface UserRepository extends MongoRepository<UserDocument, BigInteger>, CustomUserRepository {

    Optional<User> findByUserNameAndPassword(String username, String password);

    Optional<User> findByUserName(String username);

    Optional<User> findById(String userId);
}
