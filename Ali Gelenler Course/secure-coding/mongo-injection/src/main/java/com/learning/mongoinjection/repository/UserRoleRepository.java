package com.learning.mongoinjection.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.mongoinjection.collections.UserRoleDocument;

public interface UserRoleRepository extends MongoRepository<UserRoleDocument, BigInteger> {

	Optional<List<UserRoleDocument>> findByUserId(BigInteger userId);
}
