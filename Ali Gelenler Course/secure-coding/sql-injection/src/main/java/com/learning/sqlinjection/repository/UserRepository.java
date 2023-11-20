package com.learning.sqlinjection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.sqlinjection.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {
}
