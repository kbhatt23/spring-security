package com.learning.springsecurity.controllers.encodeencrypthash;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.dtos.OriginalStringRequest;
import com.learning.springsecurity.util.EncodeDecodeUtil;

@RestController
@RequestMapping
public class EncodeDecodeController {

	@GetMapping(value = "/encode/base64", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.TEXT_PLAIN_VALUE)
	public String encodeBase64(@RequestBody(required = true) OriginalStringRequest originalStringRequest) {
		return EncodeDecodeUtil.encodeBase64(originalStringRequest.getData());
	}
	
	@GetMapping(value = "/decode/base64", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.TEXT_PLAIN_VALUE)
	public String decodeBase64(@RequestBody(required = true) OriginalStringRequest originalStringRequest) {
		return EncodeDecodeUtil.decodeBase64(originalStringRequest.getData());
	}
	
	@GetMapping(value = "/encode/url", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.TEXT_PLAIN_VALUE)
	public String encodeURL(@RequestBody(required = true) OriginalStringRequest originalStringRequest) {
		return EncodeDecodeUtil.encodeURL(originalStringRequest.getData());
	}
	
	@GetMapping(value = "/decode/url", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.TEXT_PLAIN_VALUE)
	public String decodeURL(@RequestBody(required = true) OriginalStringRequest originalStringRequest) {
		return EncodeDecodeUtil.decodeURL(originalStringRequest.getData());
	}
}
