package com.learning.springsecurity.constants;

public class JWTAuthConstants {

	private JWTAuthConstants() {}

	public static final String REQUEST_ATTRIBUTE_USERNAME = "request-username";
	
	public static final String SESSION_ATTRIBUTE_USERNAME = "session-username";
	
	public static final String JWT_ISSUER = "kbhatt23@learning.com";
	
	public static final String[] JWT_AUDIENCE = {"http://kbhatt23@learning.com", "https://kbhatt23@learning.com"};
	
	public static final String JWT_HMAC_SECRET = "theHmacSecretKeyForJwt";
	
	public static final String JWT_PRIVATE_CLAIM_VALUE = "private-claim-value";
	
	public static final String JWT_PRIVATE_CLAIM_NAME = "private-claim-name";
	
	public static final long JWT_EXPIRY_MINUTES= 1l;
	
	public static final String JWT_OTHER_ATTRIBUTE_CLAIM = "other-claim-name";
	
	public static final String JWT_ID = "JWT_ID";
	
	public static final String JWE_ENCRYPTION_KEY = "TheMandatory32BytesEncryptionKey";

}
