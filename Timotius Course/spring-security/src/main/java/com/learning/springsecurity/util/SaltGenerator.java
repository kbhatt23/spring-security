package com.learning.springsecurity.util;
import java.security.SecureRandom;

public class SaltGenerator {
	private static final SecureRandom RANDOM = new SecureRandom();
    public static String generateSaltHex() {
        byte[] salt = new byte[16]; // 16 bytes is a common size for salt
        RANDOM.nextBytes(salt);
        return bytesToHex(salt);
    }
    
    public static String generateSaltHex(int size) {
        byte[] salt = new byte[size]; // 16 bytes is a common size for salt
        RANDOM.nextBytes(salt);
        return bytesToHex(salt);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
