package com.learning.springsecurity.helloworldsecurity.repository;

import org.springframework.data.repository.CrudRepository;

import com.learning.springsecurity.helloworldsecurity.model.MyAppUser;

public interface UserRepository extends CrudRepository<MyAppUser, String> {

	MyAppUser findByUsername(String username);
	MyAppUser findByEmail(String email);
	
}
