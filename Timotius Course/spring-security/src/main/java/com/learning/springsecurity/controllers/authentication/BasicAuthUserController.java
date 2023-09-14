package com.learning.springsecurity.controllers.authentication;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.dtos.BasicAuthUserDTO;
import com.learning.springsecurity.entity.BasicAuthUser;
import com.learning.springsecurity.repository.BasicAuthUserRepository;
import com.learning.springsecurity.util.EncryptDecryptUtil;
import com.learning.springsecurity.util.HashUtil;
import com.learning.springsecurity.util.SaltGenerator;

@RestController
@RequestMapping("/auth/basic")
public class BasicAuthUserController {

	//we need to secure/restrict this api only using basic auth
	@GetMapping(value = "/time",  produces = MediaType.TEXT_PLAIN_VALUE)
	public String time() {
		return "Current time is: "+LocalTime.now();
	}
	
	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository; 
	
	public static final String SECRET = "MySecretKey12345";
	
	@PostMapping( produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> createUser(@RequestBody(required = true) BasicAuthUserDTO basicAuthUserDTO) throws Exception {
		
		BasicAuthUser basicAuthUser = new BasicAuthUser();
		basicAuthUser.setDisplayName(basicAuthUserDTO.getDisplayName());
		basicAuthUser.setUsername(EncryptDecryptUtil.encryptAes(basicAuthUserDTO.getUsername().toLowerCase(), SECRET));
		String salt = SaltGenerator.generateSaltHex();
		basicAuthUser.setSalt(salt);
		basicAuthUser.setPasswordHash(HashUtil.sha256(basicAuthUserDTO.getPassword(), salt));
		
		basicAuthUserRepository.save(basicAuthUser);
		
		return ResponseEntity.created(null).body("user created succesfully");
	}
}
