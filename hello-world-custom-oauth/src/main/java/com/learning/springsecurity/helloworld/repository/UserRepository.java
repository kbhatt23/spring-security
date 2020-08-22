package com.learning.springsecurity.helloworld.repository;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<MyAppUser, String> {

	MyAppUser findByUsername(String username);
	MyAppUser findByEmail(String email);
	
}
