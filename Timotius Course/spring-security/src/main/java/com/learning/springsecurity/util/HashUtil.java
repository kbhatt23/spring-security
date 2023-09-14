package com.learning.springsecurity.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

public class HashUtil {

	private HashUtil() {}

	public static String sha256(String original, String salt) throws NoSuchAlgorithmException {
		var originalWithSalt = StringUtils.join(salt, original);
		var digest = MessageDigest.getInstance("SHA-256");
		var hash = digest.digest(originalWithSalt.getBytes(StandardCharsets.UTF_8));

		return new String(Hex.encode(hash));
	}

	public static boolean isSha256Match(String original, String salt, String hashValue)
			throws NoSuchAlgorithmException {
		var reHashValue = sha256(original, salt);
		return StringUtils.equals(hashValue, reHashValue);
	}

}
