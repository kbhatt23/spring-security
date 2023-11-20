package com.learning.sqlinjection.repository;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.sqlinjection.entity.UserDetailEntity;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, UUID> {

	Optional<UserDetailEntity> findByUserId(BigInteger userId);
}