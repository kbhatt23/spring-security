package com.learning.springsecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.springsecurity.entity.BasicAuthUser;

public interface BasicAuthUserRepository extends JpaRepository<BasicAuthUser, Integer>{

	Optional<BasicAuthUser> findByUsername(String encryptedUsername);
}
