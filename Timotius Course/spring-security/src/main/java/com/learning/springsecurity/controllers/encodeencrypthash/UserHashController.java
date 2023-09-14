package com.learning.springsecurity.controllers.encodeencrypthash;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.dtos.UserHashDetails;
import com.learning.springsecurity.entity.UserHashCredentials;
import com.learning.springsecurity.repository.UserHashRepository;
import com.learning.springsecurity.util.HashUtil;
import com.learning.springsecurity.util.SaltGenerator;

@RestController
@RequestMapping("/user-hash")
public class UserHashController {
	
	@Autowired
	private UserHashRepository userHashRepository;
	
	@PostMapping("/register")
	public ResponseEntity<String> createUser(@RequestBody UserHashDetails userHashDetails){
		String userName = userHashDetails.getUserName();
		if(userHashRepository.findById(userName).isPresent()) {
			return ResponseEntity.badRequest().body("user already exist");
		}
		

		String randomSalt = SaltGenerator.generateSaltHex();
		try {
			String hash = HashUtil.sha256(userHashDetails.getPassword(), randomSalt);
			userHashRepository.save(new UserHashCredentials(userName, hash, randomSalt));
			return ResponseEntity.ok().body("user created succesfully");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.internalServerError().body("error occurred");
	}
	
	@PostMapping("/validate")
	public ResponseEntity<String> validateUser(@RequestBody UserHashDetails userHashDetails){
		Optional<UserHashCredentials> userOptional = userHashRepository.findById(userHashDetails.getUserName());
		if(userOptional.isPresent()) {
			UserHashCredentials userHashCredentials = userOptional.get();
			try {
				boolean isValid = HashUtil.isSha256Match(userHashDetails.getPassword(), userHashCredentials.getSalt(), userHashCredentials.getHashedPassword());
				if(isValid)
					return ResponseEntity.ok().body("validated succesfully");
					
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		

		
		
		return ResponseEntity.internalServerError().body("validation failed");
	}
	
}
