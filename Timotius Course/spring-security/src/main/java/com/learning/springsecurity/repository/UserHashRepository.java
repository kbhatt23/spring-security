package com.learning.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.springsecurity.entity.UserHashCredentials;

@Repository
public interface UserHashRepository extends JpaRepository<UserHashCredentials, String>{

}
