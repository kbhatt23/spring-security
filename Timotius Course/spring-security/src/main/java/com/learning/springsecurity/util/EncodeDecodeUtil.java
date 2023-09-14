package com.learning.springsecurity.util;

import java.nio.charset.StandardCharsets;

import org.springframework.util.Base64Utils;
import org.springframework.web.util.UriUtils;

public class EncodeDecodeUtil {

	private EncodeDecodeUtil() {}
	
	public static String encodeBase64(String original) {
		handleInput(original , "encodeBase64");
		
		return Base64Utils.encodeToString(original.getBytes(StandardCharsets.UTF_8));
	}
	
	public static String decodeBase64(String encoded) {
		handleInput(encoded , "decodeBase64");
		
		return new String(Base64Utils.decodeFromString(encoded) , StandardCharsets.UTF_8);
	}
	
	public static String encodeURL(String originalURL) {
		handleInput(originalURL , "encodeURL");
		
		return UriUtils.encode(originalURL, StandardCharsets.UTF_8);
	}
	
	public static String decodeURL(String encodedURL) {
		handleInput(encodedURL , "decodeURL");
		
		return UriUtils.decode(encodedURL, StandardCharsets.UTF_8);
	}

	private static void handleInput(String original  , String methodName) {
		if(original == null || original.isBlank())
			throw new IllegalArgumentException(methodName+ ": input string can not be null or empty");
	}
}
