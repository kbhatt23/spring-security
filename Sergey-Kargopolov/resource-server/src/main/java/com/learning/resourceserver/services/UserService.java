package com.learning.resourceserver.services;

import org.springframework.stereotype.Service;

import com.learning.resourceserver.dtos.UserDTO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService {

	public UserDTO findUser(String id) {
		log.info("findUser: Finding user with id:{} ", id);

		return UserDTO.builder().id(id).build();
	}

}
