package com.learning.springsecurity.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.springsecurity.entity.BasicApiKey;

public interface BasicApiKeyRepository extends JpaRepository<BasicApiKey, Integer>{

	public Optional<BasicApiKey> findByApiKeyAndExpiredAtAfter(String apiKey , LocalDateTime time);
}
