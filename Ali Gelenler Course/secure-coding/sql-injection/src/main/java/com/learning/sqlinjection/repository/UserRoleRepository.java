package com.learning.sqlinjection.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.sqlinjection.entity.UserRoleEntity;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<List<UserRoleEntity>> findByUserId(BigInteger userId);
}
