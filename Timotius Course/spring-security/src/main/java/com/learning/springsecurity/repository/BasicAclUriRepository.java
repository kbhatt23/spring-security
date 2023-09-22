package com.learning.springsecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.springsecurity.entity.BasicAclUri;

public interface BasicAclUriRepository extends JpaRepository<BasicAclUri, Integer>{
	
	public Optional<BasicAclUri> findByUriAndMethod(String uri , String method);

}
