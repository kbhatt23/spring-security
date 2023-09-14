package com.learning.springsecurity.controllers.hmac;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.dtos.HmacRequest;
import com.learning.springsecurity.util.HmacUtil;

@RestController
@RequestMapping("/hmac")
public class HmacController {
	// move to hashicorp
	public static final String SECRET_KEY = "The123HmacSecretKey";

	// API to generate HMAC token
	// Generate HMAC token for specific request body with specific time to use the
	// token
	// Expirable Token with authorization using secret key and data integrity using
	// hmac hash validation
	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
	public String generateHMAC(@RequestHeader(name = "X-Verb-Calculate", required = true) String verbCalculate,
			@RequestHeader(name = "X-Uri-Calculate", required = true) String uriCalculate,
			@RequestHeader(name = "X-Register-Date", required = true) String registerDate,
			@RequestHeader(name = "X-Nonce", required = true) String nonce,
			@RequestBody(required = true) HmacRequest requestBody) throws Exception {

		// create request specific time limit token
		// authentication + data integrity across applications
		var hmacMessage = HmacUtil.constructHmacMessage(verbCalculate, uriCalculate, requestBody.getAmount(),
				requestBody.getFullName(), registerDate, nonce);

		return HmacUtil.generateHmacToken(hmacMessage, SECRET_KEY);
	}

	

	//demo api
	//this api is hmac authentication and managed
	//so request message should be prepared in same
	//filter will validate hmac token
	@RequestMapping(value = "/info", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String info(@RequestBody(required = true) HmacRequest original) {
		return "The request body is " + original.toString();
	}
}
