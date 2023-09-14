package com.learning.springsecurity.controllers.encodeencrypthash;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.dtos.OriginalStringRequest;
import com.learning.springsecurity.util.EncryptDecryptUtil;

@RestController
public class EncryptDecryptController {

	// must be 128 / 192 / 256 bit (16 / 24 / 32 chars)
	// in reality, don't save this secret key in code, store in hashicorp vault
	private static final String SECRET_KEY = "MySecretKey12345";

	@GetMapping(value = "/encrypt/aes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String encrypt(@RequestBody(required = true) OriginalStringRequest originalStringRequest) throws Exception {
		return EncryptDecryptUtil.encryptAes(originalStringRequest.getData(), SECRET_KEY);
	}

	@GetMapping(value = "/decrypt/aes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String decrypt(@RequestBody(required = true) OriginalStringRequest originalStringRequest) throws Exception {
		return EncryptDecryptUtil.decryptAes(originalStringRequest.getData(), SECRET_KEY);
	}
}
