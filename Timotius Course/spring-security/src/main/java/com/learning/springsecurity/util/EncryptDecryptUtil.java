package com.learning.springsecurity.util;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class EncryptDecryptUtil {
	
	private EncryptDecryptUtil() {}
	
	private static final String ALGORITHM = "AES";
	private static final String AES_TRANSFORMATION = "AES/CBC/PKCS7Padding";
	// use unique value for 16 bytes initialization vector
	//move IV and secretkey to hashicorp vault
	private static final String IV = "LqV4g8gT1DqjSPB6";
	
	
	private static IvParameterSpec paramSpec;
	private static Cipher cipher;
	
	static {
		Security.addProvider(new BouncyCastleProvider());
		paramSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

		try {
			cipher = Cipher.getInstance(AES_TRANSFORMATION);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	//move the secret key to hashicorp vault
	public static String encryptAes(String original, String secretKey) throws Exception {
		final var secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, paramSpec);

		var resultBytes = cipher.doFinal(original.getBytes(StandardCharsets.UTF_8));

		return new String(Hex.encode(resultBytes));
	}
	
	public static String decryptAes(String encrypted, String secretKey) throws Exception {
		final var secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, paramSpec);

		byte[] encryptedBytes = Hex.decode(encrypted);
		var resultBytes = cipher.doFinal(encryptedBytes);

		return new String(resultBytes);
	}
}
