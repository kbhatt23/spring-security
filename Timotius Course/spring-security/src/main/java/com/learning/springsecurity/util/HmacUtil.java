package com.learning.springsecurity.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

public class HmacUtil {

	private HmacUtil() {
	}

	private static final String HMAC_SHA256 = "HmacSHA256";
	private static final String MESSAGE_DELIMITER = "\n";

	public static String generateHmacToken(String message, String secretKey)
			throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance(HMAC_SHA256);
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256);
		mac.init(secretKeySpec);

		var hmacBytes = mac.doFinal(message.getBytes());

		return new String(Hex.encode(hmacBytes));
	}

	public static boolean validateHmacToken(String message, String secretKey, String hmac)
			throws InvalidKeyException, NoSuchAlgorithmException {
		var reHmacValue = generateHmacToken(message, secretKey);

		return StringUtils.equals(hmac, reHmacValue);
	}

	// message preparation logic
	// hmac token valid for this message only
	public static String constructHmacMessage(String verb, String requestURI, int amount, String fullName,
			String xRegisterDate, String nonce) {
		var sb = new StringBuilder();

		sb.append(verb.toLowerCase());
		sb.append(MESSAGE_DELIMITER);
		sb.append(requestURI);
		sb.append(MESSAGE_DELIMITER);
		sb.append(amount);
		sb.append(MESSAGE_DELIMITER);
		sb.append(fullName);
		sb.append(MESSAGE_DELIMITER);
		sb.append(xRegisterDate);
		sb.append(nonce);

		return sb.toString();
	}
}
