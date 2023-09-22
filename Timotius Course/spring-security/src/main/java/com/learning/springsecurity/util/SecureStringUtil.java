package com.learning.springsecurity.util;

import java.security.MessageDigest;

import com.google.common.base.Charsets;

public class SecureStringUtil {


	public static boolean equals(String first, String second) {
		return MessageDigest.isEqual(first.getBytes(Charsets.UTF_8), second.getBytes(Charsets.UTF_8));
	}
}
